package com.ald.finance.service;

import com.ald.finance.common.enums.CourseRecordEnum;
import com.ald.finance.common.enums.UserRoleEnum;
import com.ald.finance.common.utils.MyDateUtils;
import com.ald.finance.common.web.BusinessException;
import com.ald.finance.dal.entity.TeacherCourseRecord;
import com.ald.finance.dal.entity.TeacherLeaveRecord;
import com.ald.finance.dal.entity.User;
import com.ald.finance.dal.repository.TeacherCourseRecordRepository;
import com.ald.finance.dal.repository.TeacherLeaveRecordRepository;
import com.ald.finance.service.dto.TeacherLeaveRecordDTO;
import com.ald.finance.service.query.TeacherLeaveRecordQuery;
import com.ald.finance.service.utils.TeacherCourseUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by liang3.zhang on 2018/5/4.
 */
@Service
public class TeacherLeaveRecordService {
    
    @Autowired
    TeacherLeaveRecordRepository teacherLeaveRecordRepository;
    
    @Autowired
    TeacherCourseRecordRepository teacherCourseRecordRepository;
    
    @Autowired
    UserService userService;
    
    /**
     * 查询学生请假记录
     *
     * @param query
     * @param pageable
     * @return
     */
    public Page<TeacherLeaveRecordDTO> page(TeacherLeaveRecordQuery query, Pageable pageable) {
        Specification<TeacherLeaveRecord> specification = getSpecification(query);
        Page<TeacherLeaveRecord> page = teacherLeaveRecordRepository.findAll(specification, pageable);
        List<Long> userIds = page.getContent().stream().map(s -> s.getUserId()).collect(Collectors.toList());
        final Map<Long, User> userMap = userService.loadUserMap(userIds);
        return page.map(s -> {
            TeacherLeaveRecordDTO dto = new TeacherLeaveRecordDTO();
            BeanUtils.copyProperties(s, dto);
            User user = userMap.get(s.getUserId());
            if (user != null) {
                dto.setTeacherMobile(user.getUserMobile());
                dto.setTeacherName(user.getUserNickname());
            }
            return dto;
        });
    }
    
    /**
     * 老师请假
     *
     * @param record
     * @param
     * @return
     */
    public boolean leave(TeacherLeaveRecord record) {
        // 根据请假日期查询课程
        User user = userService.findOne(record.getUserId());
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        TeacherCourseUtils.valid(record.getDate(), record.getDate());
        // 查询课程
        List<TeacherCourseRecord> list = teacherCourseRecordRepository
            .findAllByCourseRecordDateAndUserId(record.getDate(), record.getUserId());
        for (TeacherCourseRecord courseRecord : list) {
            if (courseRecord.getCourseRecordIndex().equals(record.getIndex())) {
                record.setCourseRecordId(courseRecord.getId());
                break;
            }
        }
        record.setStatus(0);
        record.setCreateTime(LocalDateTime.now());
        record.setUpdateTime(LocalDateTime.now());
        teacherLeaveRecordRepository.save(record);
        return true;
    }
    
    // 同意请假
    @Transactional
    public boolean approval(Long id, Integer status) {
        TeacherLeaveRecord record = teacherLeaveRecordRepository.findOne(id);
        if (record == null) {
            throw new BusinessException("请假记录不存在");
        }
        if (record.getStatus() != 0) {
            throw new BusinessException("请假申请已审批，不能重复审批");
        }
        record.setStatus(status);
        record.setUpdateTime(LocalDateTime.now());
        teacherLeaveRecordRepository.save(record);
        // 不同意，则不需要更改老师课程
        if (status != 1)
            return true;
        if (record.getCourseRecordId() == null)
            return true;
        TeacherCourseRecord teacherCourseRecord = teacherCourseRecordRepository.findOne(record.getCourseRecordId());
        if (teacherCourseRecord != null) {
            // 更新老师课程ID
            teacherCourseRecord.setCourseRecordStatus(CourseRecordEnum.leave.getCode());
            teacherCourseRecordRepository.save(teacherCourseRecord);
            // 发送消息给学生
        }
        return true;
    }
    
    // 删除老师的请假记录
    public boolean delete(Long id, User user) {
        // 判断权限
        TeacherLeaveRecord record = teacherLeaveRecordRepository.findOne(id);
        checkDeleteCord(record, user);
        teacherLeaveRecordRepository.delete(record);
        return true;
    }
    
    private void checkDeleteCord(TeacherLeaveRecord record, User user) {
        if (record == null) {
            throw new BusinessException("请假记录不存在");
        }
        if (record.getStatus() != 0) {
            throw new BusinessException("已审批的请假记录不能删除");
        }
        if (!MyDateUtils.isAfterNow(record.getDate())) {
            throw new BusinessException("只能删除请假日期为以后的记录");
        }
        if (UserRoleEnum.Teacher.getRoleId().equals(user.getUserRole()) && !user.getId().equals(record.getUserId())) {
            throw new BusinessException("无权限删除");
        }
    }
    
    private Specification<TeacherLeaveRecord> getSpecification(final TeacherLeaveRecordQuery query) {
        return new Specification<TeacherLeaveRecord>() {
            
            @Override
            public Predicate toPredicate(Root<TeacherLeaveRecord> root, CriteriaQuery<?> criteriaQuery,
                CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList = new ArrayList<Predicate>();
                if (query.getTeacherId() != null) {
                    predicateList.add(criteriaBuilder.equal(root.get("userId"), query.getTeacherId()));
                }
                if (query.getStatus() != null) {
                    predicateList.add(criteriaBuilder.equal(root.get("status"), query.getStatus()));
                }
                if (StringUtils.isNotBlank(query.getStart())) {
                    predicateList
                        .add(criteriaBuilder.greaterThanOrEqualTo(root.get("date"), LocalDate.parse(query.getStart())));
                }
                if (StringUtils.isNotBlank(query.getEnd())) {
                    predicateList
                        .add(criteriaBuilder.lessThanOrEqualTo(root.get("date"), LocalDate.parse(query.getEnd())));
                }
                Predicate[] predicates = new Predicate[predicateList.size()];
                Order order = criteriaBuilder.desc(root.get("updateTime").as(Date.class));
                return criteriaQuery.where(predicateList.toArray(predicates)).orderBy(order).getRestriction();
            }
        };
    }
}