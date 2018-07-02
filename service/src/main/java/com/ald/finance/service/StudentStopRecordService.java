package com.ald.finance.service;


import com.ald.finance.dal.entity.*;
import com.ald.finance.common.enums.UserRoleEnum;
import com.ald.finance.dal.repository.StudentStopRecordRepository;
import com.ald.finance.common.web.BusinessException;
import com.ald.finance.service.dto.StudentStopRecordDTO;
import com.ald.finance.service.utils.TeacherCourseUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by liang3.zhang on 2018/5/4.
 */
@Service
public class StudentStopRecordService {

    @Autowired
    StudentStopRecordRepository studentStopRecordRepository;

    @Autowired
    UserService userService;

    @Autowired
    SysStudentCourseService sysStudentCourseService;

    /**
     * 停课
     *
     * @param record
     * @return
     */
    public Boolean stop(StudentStopRecord record) {
        TeacherCourseUtils.valid(record.getStartTime(), record.getEndTime());
        record.setCreateTime(LocalDateTime.now());
        record.setStatus(0);
        record.setUpdateTime(LocalDateTime.now());
        studentStopRecordRepository.save(record);
        return true;
    }

    /**
     * 查询学生对应的停课记录
     *
     * @param
     * @return
     */
    public Page<StudentStopRecordDTO> find(Long userId, Pageable pageable) {
        Specification<StudentStopRecord> specification = getSpecification(userId);
        Page<StudentStopRecord> page = studentStopRecordRepository.findAll(specification, pageable);
        if (CollectionUtils.isEmpty(page.getContent()))
            return new PageImpl<StudentStopRecordDTO>(Collections.EMPTY_LIST);
        List<Long> users = page.getContent().stream().map(s -> s.getUserId()).collect(Collectors.toList());
        final Map<Long, User> userMap = userService.loadUserMap(users);
        return page.map(s -> {
            StudentStopRecordDTO dto = new StudentStopRecordDTO();
            BeanUtils.copyProperties(s, dto);
            User user = userMap.get(s.getUserId());
            if (user != null) {
                dto.setStudentMobile(user.getUserMobile());
                dto.setStudentName(user.getUserNickname());
            }
            return dto;
        });
    }

    // 删除停课记录
    public Boolean delete(Long id, User user) {
        StudentStopRecord stopRecord = studentStopRecordRepository.findOne(id);
        if (stopRecord == null) {
            throw new BusinessException("停课记录不存在");
        }
        if (stopRecord.getStatus() != 0) {
            throw new BusinessException("已审批过的停课申请不能删除");
        }
        if (!user.getUserRole().equals(UserRoleEnum.Admin.getRoleId())) {
            throw new BusinessException("无权限删除别人的停课记录");
        }
        studentStopRecordRepository.delete(stopRecord);
        return true;
    }

    // 审批
    @Transactional
    public Boolean approval(Long id, Integer status) {
        StudentStopRecord record = studentStopRecordRepository.findOne(id);
        if (record == null) {
            throw new BusinessException("停课记录不存在");
        }
        if (record.getStatus() != 0) {
            throw new BusinessException("已审批过的停课申请不能重复审批");
        }
        record.setStatus(status);
        studentStopRecordRepository.save(record);
        //更改相应的记录
        sysStudentCourseService.studentStop(record);
        return true;
    }

    // 构造查询条件
    private Specification<StudentStopRecord> getSpecification(final Long userId) {
        return new Specification<StudentStopRecord>() {

            @Override
            public Predicate toPredicate(Root<StudentStopRecord> root, CriteriaQuery<?> criteriaQuery,
                                         CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList = new ArrayList<Predicate>();
                if (userId != null) {
                    predicateList.add(criteriaBuilder.equal(root.get("userId"), userId));
                }
                Predicate[] predicates = new Predicate[predicateList.size()];
                Order order = criteriaBuilder.desc(root.get("createTime").as(Date.class));
                return criteriaQuery.where(predicateList.toArray(predicates)).orderBy(order).getRestriction();
            }
        };
    }
}