package com.ald.finance.service;

import com.ald.finance.dal.entity.TeacherCourseRecord;
import com.ald.finance.dal.entity.User;
import com.ald.finance.dal.repository.TeacherCourseRecordRepository;
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
public class TeacherCourseRecordService {

    @Autowired
    TeacherCourseRecordRepository teacherCourseRecordRepository;

    @Autowired
    UserService userService;

    /**
     * 查询上课记录
     *
     * @param query
     * @return
     */
    public Page<TeacherCourseRecordDTO> find(TeacherCourseRecordQuery query, Pageable pageable) {
        Specification<TeacherCourseRecord> specification = getSpecification(query);
        Page<TeacherCourseRecord> result = teacherCourseRecordRepository.findAll(specification, pageable);
        List<Long> list = result.getContent().stream().map(a -> a.getUserId()).collect(Collectors.toList());
        final Map<Long, User> map = userService.loadUserMap(list);
        return result.map(s -> {
            TeacherCourseRecordDTO dto = new TeacherCourseRecordDTO();
            BeanUtils.copyProperties(s, dto);
            User teacher = map.get(s.getUserId());
            if (teacher != null) {
                dto.setTeacherName(teacher.getUserNickname());
                dto.setTeacherMobile(teacher.getUserMobile());
            }
            dto.setWeekIndex(s.getCourseRecordDate().getDayOfWeek().getValue());
            return dto;
        });
    }

    private Specification<TeacherCourseRecord> getSpecification(final TeacherCourseRecordQuery query) {
        return new Specification<TeacherCourseRecord>() {

            @Override
            public Predicate toPredicate(Root<TeacherCourseRecord> root, CriteriaQuery<?> criteriaQuery,
                                         CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList = new ArrayList<Predicate>();
                //根据课程ID查询
                if (query.getCourseId() != null) {
                    predicateList.add(criteriaBuilder.equal(root.get("courseId"), query.getCourseId()));
                }
                //根据教师ID查询
                if (query.getUserId() != null) {
                    predicateList.add(criteriaBuilder.equal(root.get("userId"), query.getUserId()));
                }
                //根据时间查询
                if (query.getCourseDate() != null) {
                    predicateList.add(criteriaBuilder.equal(root.get("courseRecordDate"), query.getCourseDate()));
                }
                //根据时间端查询
                if (query.getStart() != null) {
                    predicateList.add(criteriaBuilder.greaterThanOrEqualTo(root.get("courseRecordDate"), query.getStart()));
                }
                if (query.getEnd() != null) {
                    predicateList.add(criteriaBuilder.lessThanOrEqualTo(root.get("courseRecordDate"), query.getEnd()));
                }
                Predicate[] predicates = new Predicate[predicateList.size()];
                Order order = criteriaBuilder.desc(root.get("updateTime").as(Date.class));
                return criteriaQuery.where(predicateList.toArray(predicates)).orderBy(order).getRestriction();
            }
        };
    }
}
