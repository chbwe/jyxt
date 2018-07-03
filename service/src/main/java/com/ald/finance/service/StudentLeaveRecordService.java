package com.ald.finance.service;

import com.ald.finance.common.enums.UserRoleEnum;
import com.ald.finance.common.web.BusinessException;
import com.ald.finance.dal.entity.StudentBuyRecord;
import com.ald.finance.dal.entity.StudentCourseRecord;
import com.ald.finance.dal.entity.StudentLeaveRecord;
import com.ald.finance.dal.entity.User;
import com.ald.finance.dal.repository.StudentBuyRecordRepository;
import com.ald.finance.dal.repository.StudentCourseRecordRepository;
import com.ald.finance.dal.repository.StudentLeaveRecordRepository;
import com.ald.finance.service.dto.StudentLeaveRecordDTO;
import com.ald.finance.service.utils.TeacherCourseUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by liang3.zhang on 2018/6/1.
 */
@Service
public class StudentLeaveRecordService {
    
    @Autowired
    StudentLeaveRecordRepository studentLeaveRecordRepository;
    
    @Autowired
    StudentCourseRecordRepository studentCourseRecordRepository;
    
    @Autowired
    StudentBuyRecordRepository studentBuyRecordRepository;
    
    @Autowired
    UserService userService;
    
    @Autowired
    SysStudentCourseService sysStudentCourseService;
    
    public List<StudentLeaveRecord> listByStudentId(Long studentId) {
        return studentLeaveRecordRepository.findAllByUserId(studentId);
    }
    
    /**
     * 查询学生请假记录
     *
     * @param teacherId
     * @param pageable
     * @return
     */
    public Page<StudentLeaveRecordDTO> page(Long teacherId, Pageable pageable) {
        Specification<StudentLeaveRecord> specification = getSpecification(teacherId);
        Page<StudentLeaveRecord> page = studentLeaveRecordRepository.findAll(specification, pageable);
        List<Long> userIds = page.getContent().stream().map(s -> s.getUserId()).collect(Collectors.toList());
        userIds.addAll(page.getContent().stream().map(s -> s.getTeacherId()).collect(Collectors.toList()));
        final Map<Long, User> userMap = userService.loadUserMap(userIds);
        return page.map(s -> {
            StudentLeaveRecordDTO dto = new StudentLeaveRecordDTO();
            BeanUtils.copyProperties(s, dto);
            User user = userMap.get(s.getUserId());
            if (user != null) {
                dto.setStudentMobile(user.getUserMobile());
                dto.setStudentName(user.getUserNickname());
            }
            user = userMap.get(s.getTeacherId());
            if (user != null) {
                dto.setTeacherName(user.getUserMobile());
                dto.setTeacherMobile(user.getUserNickname());
            }
            return dto;
        });
    }
    
    /**
     * 学生请假
     *
     * @param id
     * @param user
     * @return
     */
    public Boolean leave(Long id, User user) {
        StudentCourseRecord record = studentCourseRecordRepository.findOne(id);
        if (record == null) {
            throw new BusinessException("上课记录不存在");
        }
        if (!record.getUserId().equals(user.getId())) {
            throw new BusinessException("无权限");
        }
        TeacherCourseUtils.valid(record.getCourseDate(), record.getCourseDate());
        // 保存请假记录
        StudentLeaveRecord item = new StudentLeaveRecord();
        item.setStatus(0);
        item.setCourseRecordId(id);
        item.setCreateTime(LocalDateTime.now());
        item.setUpdateTime(LocalDateTime.now());
        item.setUserId(record.getUserId());
        item.setDate(record.getCourseDate());
        item.setIndex(record.getCourseIndex());
        item.setCourseStartTime(record.getCourseStartTime());
        item.setCourseEndTime(record.getCourseEndTime());
        StudentBuyRecord studentBuyRecord = studentBuyRecordRepository.findOne(record.getBuyRecordId());
        if (studentBuyRecord != null) {
            item.setTeacherId(studentBuyRecord.getTeacherId());
        }
        studentLeaveRecordRepository.save(item);
        return true;
    }
    
    // 审批
    @Transactional
    public Boolean approval(Long id, Integer status) {
        StudentLeaveRecord record = studentLeaveRecordRepository.findOne(id);
        if (record == null) {
            throw new BusinessException("请假申请不存在");
        }
        if (record.getStatus() != 0) {
            throw new BusinessException("已审批过的请假申请不能重复审批");
        }
        record.setStatus(status);
        studentLeaveRecordRepository.save(record);
        
        // 审批不是同意，则直接返回
        if (status != 1)
            return true;
        sysStudentCourseService.studentLeave(record);
        return true;
    }
    
    /**
     * 删除学生请假申请
     *
     * @param id
     * @return
     */
    public Boolean delete(Long id, User user) {
        StudentLeaveRecord record = studentLeaveRecordRepository.findOne(id);
        if (record == null) {
            throw new BusinessException("请假申请不存在");
        }
        if (record.getStatus() != 0) {
            throw new BusinessException("已审批过的请假申请不能被删除");
        }
        if (user.getUserRole().equals(UserRoleEnum.Teacher.getRoleId()) && record.getTeacherId().equals(user.getId())) {
            studentLeaveRecordRepository.delete(id);
            return true;
        }
        if (user.getUserRole().equals(UserRoleEnum.Admin.getRoleId())) {
            studentLeaveRecordRepository.delete(id);
            return true;
        }
        throw new BusinessException("不能删除其他老师的申请");
    }
    
    private Specification<StudentLeaveRecord> getSpecification(final Long teacherId) {
        return new Specification<StudentLeaveRecord>() {
            
            @Override
            public Predicate toPredicate(Root<StudentLeaveRecord> root, CriteriaQuery<?> criteriaQuery,
                CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList = new ArrayList<Predicate>();
                if (teacherId != null) {
                    predicateList.add(criteriaBuilder.equal(root.get("teacherId"), teacherId));
                }
                Predicate[] predicates = new Predicate[predicateList.size()];
                Order order = criteriaBuilder.desc(root.get("updateTime").as(Date.class));
                return criteriaQuery.where(predicateList.toArray(predicates)).orderBy(order).getRestriction();
            }
        };
    }
}
