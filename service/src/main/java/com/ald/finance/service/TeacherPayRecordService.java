package com.ald.finance.service;


import com.ald.finance.dal.entity.TeacherPayRecord;
import com.ald.finance.dal.entity.User;
import com.ald.finance.common.enums.UserRoleEnum;
import com.ald.finance.service.dto.TeacherPayRecordDTO;
import com.ald.finance.service.query.TeacherPayRecordQuery;
import com.ald.finance.dal.repository.TeacherPayRecordRepository;
import com.ald.finance.common.web.BusinessException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by liang3.zhang on 2018/5/4.
 */
@Service
public class TeacherPayRecordService {
    
    @Autowired
    TeacherPayRecordRepository teacherPayRecordRepository;
    
    @Autowired
    UserService userService;
    
    /**
     * 查询教师对应的当月结算记录
     * 
     * @param teacherId
     * @param year
     * @param month
     * @return
     */
    public TeacherPayRecord findOneByTeacherIdAndYearAndMonth(Long teacherId, Integer year, Integer month) {
        return teacherPayRecordRepository.findOneByUserIdAndYearAndMonth(teacherId, year, month);
    }
    
    /**
     * 批量保存记录
     * 
     * @param payRecords
     * @return
     */
    public List<TeacherPayRecord> save(List<TeacherPayRecord> payRecords) {
        return teacherPayRecordRepository.save(payRecords);
    }
    
    /**
     * 支付
     * 
     * @param id
     * @return
     */
    public Boolean pay(Long id, User user) {
        TeacherPayRecord record = teacherPayRecordRepository.findOne(id);
        if (record == null) {
            throw new BusinessException("记录不存在");
        }
        if (user.getUserRole().equals(UserRoleEnum.Teacher.getRoleId()) && !record.getUserId().equals(user.getId())) {
            throw new BusinessException("不能结算别人的工资");
        }
        if (record.getStatus() != 0) {
            throw new BusinessException("已结算");
        }
        LocalDate date = LocalDate.now();
        if (record.getYear() == date.getYear() && record.getMonth() == date.getMonth().getValue()) {
            throw new BusinessException("当月不能结算");
        }
        record.setStatus(1);
        record.setPayTime(LocalDateTime.now());
        teacherPayRecordRepository.save(record);
        return true;
    }
    
    /**
     * 根据条件查询
     *
     * @param vm
     * @param pageable
     * @return
     */
    public Page<TeacherPayRecordDTO> find(TeacherPayRecordQuery vm, Pageable pageable) {
        Specification<TeacherPayRecord> specification = getSpecification(vm);
        Page<TeacherPayRecord> result = teacherPayRecordRepository.findAll(specification, pageable);
        final Map<Long, User> map = userService
            .loadUserMap(result.getContent().stream().map(a -> a.getUserId()).collect(Collectors.toList()));
        return result.map(s -> {
            TeacherPayRecordDTO dto = new TeacherPayRecordDTO();
            BeanUtils.copyProperties(s, dto);
            User user = map.get(s.getUserId());
            if (user != null) {
                dto.setTeacherName(user.getUserNickname());
                dto.setTeacherMobile(user.getUserMobile());
            }
            return dto;
        });
    }
    
    /**
     * 构造查询条件
     * 
     * @param query
     * @return
     */
    private Specification<TeacherPayRecord> getSpecification(final TeacherPayRecordQuery query) {
        return new Specification<TeacherPayRecord>() {
            
            @Override
            public Predicate toPredicate(Root<TeacherPayRecord> root, CriteriaQuery<?> criteriaQuery,
                CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList = new ArrayList<Predicate>();
                // 根据教师ID查询
                if (query.getTeacherId() != null) {
                    predicateList.add(criteriaBuilder.equal(root.get("userId"), query.getTeacherId()));
                }
                // 根据状态查询
                if (query.getStatus() != null) {
                    predicateList.add(criteriaBuilder.equal(root.get("status"), query.getStatus()));
                }
                // 根据时间查询
                if (!StringUtils.isEmpty(query.getDate())) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
                    try {
                        Date date = sdf.parse(query.getDate());
                        LocalDate localDate = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault())
                            .toLocalDate();
                        predicateList.add(criteriaBuilder.equal(root.get("month"), localDate.getMonthValue()));
                        predicateList.add(criteriaBuilder.equal(root.get("year"), localDate.getYear()));
                    } catch (Exception e) {
                        throw new BusinessException("日期错误");
                    }
                }
                Predicate[] predicates = new Predicate[predicateList.size()];
                Order order = criteriaBuilder.desc(root.get("updateTime").as(Date.class));
                return criteriaQuery.where(predicateList.toArray(predicates)).orderBy(order).getRestriction();
            }
        };
    }
}
