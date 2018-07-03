package com.ald.finance.service;

import com.ald.finance.dal.entity.StudentCourseRecord;
import com.ald.finance.dal.entity.TeacherCourseRecord;
import com.ald.finance.dal.entity.User;
import com.ald.finance.dal.repository.StudentCourseRecordRepository;
import com.ald.finance.dal.repository.TeacherCourseRecordRepository;
import com.ald.finance.service.dto.StudentCourseRecordDTO;
import com.ald.finance.service.dto.TeacherCourseRecordDTO;
import com.ald.finance.service.query.TeacherCourseRecordQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by zhangliang on 2018/6/2.
 */
@Service
public class StudentCourseRecordService {

    @Autowired
    StudentCourseRecordRepository studentCourseRecordRepository;

    @Autowired
    UserService userService;

    /**
     * 查询上课记录
     *
     * @param query
     * @return
     */
    public Page<StudentCourseRecordDTO> find(TeacherCourseRecordQuery query, Pageable pageable) {
        Specification<StudentCourseRecord> specification = getSpecification(query);
        Page<StudentCourseRecord> result = studentCourseRecordRepository.findAll(specification, pageable);
        return result.map(s -> {
            StudentCourseRecordDTO dto = new StudentCourseRecordDTO();
            BeanUtils.copyProperties(s, dto);
            dto.setWeekIndex(s.getCourseDate().getDayOfWeek().getValue());
            return dto;
        });
    }

    private Specification<StudentCourseRecord> getSpecification(final TeacherCourseRecordQuery query) {
        return new Specification<StudentCourseRecord>() {

            @Override
            public Predicate toPredicate(Root<StudentCourseRecord> root, CriteriaQuery<?> criteriaQuery,
                                         CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList = new ArrayList<Predicate>();
                if (query.getUserId() != null) {
                    predicateList.add(criteriaBuilder.equal(root.get("userId"), query.getUserId()));
                }
                if (query.getStart() != null) {
                    predicateList.add(criteriaBuilder.greaterThanOrEqualTo(root.get("courseDate"), query.getStart()));
                }
                if (query.getEnd() != null) {
                    predicateList.add(criteriaBuilder.lessThanOrEqualTo(root.get("courseDate"), query.getEnd()));
                }
                Predicate[] predicates = new Predicate[predicateList.size()];
                Order order = criteriaBuilder.desc(root.get("updateTime").as(Date.class));
                return criteriaQuery.where(predicateList.toArray(predicates)).orderBy(order).getRestriction();
            }
        };
    }
}
