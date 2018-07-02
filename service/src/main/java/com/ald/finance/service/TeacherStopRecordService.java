package com.ald.finance.service;

import com.ald.finance.common.enums.UserRoleEnum;
import com.ald.finance.service.utils.TeacherCourseUtils;
import com.ald.finance.common.web.BusinessException;
import com.ald.finance.dal.entity.TeacherStopRecord;
import com.ald.finance.dal.entity.User;
import com.ald.finance.dal.repository.TeacherStopRecordRepository;
import com.ald.finance.service.dto.TeacherStopRecordDTO;
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
public class TeacherStopRecordService {
    
    @Autowired
    TeacherStopRecordRepository teacherStopRecordRepository;
    
    @Autowired
    UserService userService;
    
    /**
     *
     *
     * @param
     * @return
     */
    public Page<TeacherStopRecordDTO> find(Long userId, Pageable pageable) {
        Specification<TeacherStopRecord> specification = getSpecification(userId);
        Page<TeacherStopRecord> page = teacherStopRecordRepository.findAll(specification, pageable);
        if (CollectionUtils.isEmpty(page.getContent()))
            return new PageImpl<TeacherStopRecordDTO>(Collections.EMPTY_LIST);
        List<Long> users = page.getContent().stream().map(s -> s.getUserId()).collect(Collectors.toList());
        final Map<Long, User> userMap = userService.loadUserMap(users);
        return page.map(s -> {
            TeacherStopRecordDTO dto = new TeacherStopRecordDTO();
            BeanUtils.copyProperties(s, dto);
            User user = userMap.get(s.getUserId());
            if (user != null) {
                dto.setTeacherMobile(user.getUserMobile());
                dto.setTeacherName(user.getUserNickname());
            }
            return dto;
        });
    }
    
    // 删除停课记录
    public Boolean delete(Long id, User user) {
        TeacherStopRecord stopRecord = teacherStopRecordRepository.findOne(id);
        if (stopRecord == null) {
            throw new BusinessException("停课记录不存在");
        }
        if (stopRecord.getStatus() != 0) {
            throw new BusinessException("已审批过的停课申请不能删除");
        }
        if (!user.getUserRole().equals(UserRoleEnum.Admin.getRoleId())) {
            throw new BusinessException("无权限删除别人的停课记录");
        }
        teacherStopRecordRepository.delete(stopRecord);
        return true;
    }
    
    /**
     * 保存停课
     * 
     * @param record
     * @return
     */
    public boolean stop(TeacherStopRecord record) {
        // 根据请假日期查询课程
        TeacherCourseUtils.valid(record.getStartTime(), record.getEndTime());
        // 查询课程
        record.setStatus(0);
        record.setCreateTime(LocalDateTime.now());
        record.setUpdateTime(LocalDateTime.now());
        teacherStopRecordRepository.save(record);
        return true;
    }
    
    // 审批
    @Transactional
    public Boolean approval(Long id, Integer status) {
        TeacherStopRecord record = teacherStopRecordRepository.findOne(id);
        if (record == null) {
            throw new BusinessException("停课记录不存在");
        }
        if (record.getStatus() != 0) {
            throw new BusinessException("已审批过的停课申请不能重复审批");
        }
        record.setStatus(status);
        teacherStopRecordRepository.save(record);
        return true;
    }
    
    // 构造查询条件
    private Specification<TeacherStopRecord> getSpecification(final Long userId) {
        return new Specification<TeacherStopRecord>() {
            
            @Override
            public Predicate toPredicate(Root<TeacherStopRecord> root, CriteriaQuery<?> criteriaQuery,
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