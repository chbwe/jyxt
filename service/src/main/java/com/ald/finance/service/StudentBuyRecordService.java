package com.ald.finance.service;


import com.ald.finance.common.enums.SMSEnum;
import com.ald.finance.common.utils.SendMessageUtils;
import com.ald.finance.dal.entity.StudentBuyRecord;
import com.ald.finance.dal.entity.User;
import com.ald.finance.dal.repository.StudentBuyRecordRepository;
import com.ald.finance.service.dto.StudentBuyRecordDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by liang3.zhang on 2018/5/4.
 */
@Service
public class StudentBuyRecordService {

    @Autowired
    StudentBuyRecordRepository studentBuyRecordRepository;

    @Autowired
    UserService userService;

    @Autowired
    SysStudentCourseService sysStudentCourseService;


    /**
     * 支付失败
     *
     * @param id
     * @return
     */
    public Boolean payFail(Long id) {
        StudentBuyRecord record = studentBuyRecordRepository.findOne(id);
        if (record == null) {
            return false;
        }
        record.setStatus(-1);
        record.setUpdateTime(LocalDateTime.now());
        studentBuyRecordRepository.save(record);
        //删除课程
        sysStudentCourseService.payFail(record);
        return true;
    }

    /**
     * 支付成功
     *
     * @param id
     * @return
     */
    public Boolean paySuccess(Long id) {
        StudentBuyRecord record = studentBuyRecordRepository.findOne(id);
        if (record == null) {
            return false;
        }
        record.setStatus(1);
        record.setUpdateTime(LocalDateTime.now());
        studentBuyRecordRepository.save(record);
        //发送短信
        User user = userService.findOne(record.getUserId());
        if (user != null) {
            SendMessageUtils.sendSms(user.getUserMobile(), SMSEnum.buy, new HashMap<>());
        }
        return true;
    }
   public StudentBuyRecord findOneById(Long id){
        return studentBuyRecordRepository.findOne(id);
    }
    public List<StudentBuyRecordDTO> find(Long userId) {
        List<StudentBuyRecord> list = studentBuyRecordRepository.findAllByUserId(userId);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        final Map<Long, User> map = map(list);
        return list.stream().map(s -> convert(s, map)).collect(Collectors.toList());
    }

    private Map<Long, User> map(List<StudentBuyRecord> list) {
        // 查询ID
        List<Long> teacherIds = new ArrayList<>();
        for (StudentBuyRecord record : list) {
            teacherIds.add(record.getTeacherId());
            teacherIds.add(record.getUserId());
        }
        return userService.loadUserMap(teacherIds);
    }

    private StudentBuyRecordDTO convert(StudentBuyRecord record, Map<Long, User> map) {
        StudentBuyRecordDTO dto = new StudentBuyRecordDTO();
        BeanUtils.copyProperties(record, dto);
        User student = map.get(record.getUserId());
        if (student != null) {
            dto.setStudentName(student.getUserNickname());
            dto.setStudentMobile(student.getUserMobile());
        }
        student = map.get(record.getTeacherId());
        if (student != null) {
            dto.setTeacherName(student.getUserNickname());
            dto.setTeacherMobile(student.getUserMobile());
        }
        return dto;
    }

    /**
     * 根据条件查询
     *
     * @param
     * @param pageable
     * @return
     */
    public Page<StudentBuyRecordDTO> find(Long teacherId, Pageable pageable) {
        Specification<StudentBuyRecord> specification = getSpecification(teacherId);
        Page<StudentBuyRecord> result = studentBuyRecordRepository.findAll(specification, pageable);
        if (CollectionUtils.isEmpty(result.getContent())) {
            return new PageImpl<StudentBuyRecordDTO>(Collections.emptyList());
        }
        final Map<Long, User> map = map(result.getContent());
        // 返回结果
        return result.map(s -> convert(s, map));
    }

    private Specification<StudentBuyRecord> getSpecification(final Long teacherId) {
        return new Specification<StudentBuyRecord>() {

            @Override
            public Predicate toPredicate(Root<StudentBuyRecord> root, CriteriaQuery<?> criteriaQuery,
                                         CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList = new ArrayList<Predicate>();
                // 根据老师ID查询
                if (teacherId != null) {
                    predicateList.add(criteriaBuilder.equal(root.get("teacherId"), teacherId));
                }
                // 查询已支付过的
                predicateList.add(criteriaBuilder.notEqual(root.get("status"), 1));
                Predicate[] predicates = new Predicate[predicateList.size()];
                Order order = criteriaBuilder.desc(root.get("updateTime").as(Date.class));
                return criteriaQuery.where(predicateList.toArray(predicates)).orderBy(order).getRestriction();
            }
        };
    }
}
