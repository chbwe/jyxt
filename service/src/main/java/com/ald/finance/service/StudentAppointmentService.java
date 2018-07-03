package com.ald.finance.service;

import com.ald.finance.common.enums.SMSEnum;
import com.ald.finance.common.utils.SendMessageUtils;
import com.ald.finance.common.web.BusinessException;
import com.ald.finance.dal.entity.Holiday;
import com.ald.finance.dal.entity.StudentAppointment;
import com.ald.finance.dal.entity.TeacherCourse;
import com.ald.finance.dal.entity.User;
import com.ald.finance.dal.repository.HolidayRepository;
import com.ald.finance.dal.repository.StudentAppointmentRepository;
import com.ald.finance.dal.repository.TeacherCourseRepository;
import com.ald.finance.service.dto.StudentAppointmentDTO;
import com.ald.finance.service.query.AppointmentQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Created by liang3.zhang on 2018/5/4.
 */
@Service
public class StudentAppointmentService {
    
    @Autowired
    StudentAppointmentRepository studentAppointmentRepository;
    
    @Autowired
    TeacherCourseRepository teacherCourseRepository;
    
    @Autowired
    UserService userService;
    
    @Autowired
    HolidayRepository holidayRepository;

    public StudentAppointment save(StudentAppointment appointment, String key) {
        String courseId = key.split("_")[2];
        TeacherCourse teacherCourse = teacherCourseRepository.findOne(Long.valueOf(courseId));
        if (teacherCourse == null)
            throw new BusinessException("老师课程不存在");
        Integer weekIndex = teacherCourse.getWeekIndex();
        appointment.setCourseIndex(teacherCourse.getCourseIndex());
        appointment.setCourseStatus(-1);
        appointment.setPrice(0.1D);
        appointment.setPayStatus(0);
        appointment.setCreateTime(LocalDateTime.now());
        appointment.setUpdateTime(LocalDateTime.now());
        appointment.setCourseId(Long.valueOf(courseId));
        // 查找最近一次可预约的
        LocalDate localDate = appointment.getStartDate();
        if (!weekIndex.equals(localDate.getDayOfWeek().getValue())) {
            localDate = localDate.plusDays(7 - weekIndex + localDate.getDayOfWeek().getValue());
        }
        Holiday holiday = holidayRepository.findOneByHolidayDay(localDate);
        if (holiday != null) {
            throw new BusinessException("节假日不能预约");
        }
        appointment.setCourseDate(localDate);
        return studentAppointmentRepository.save(appointment);
    }
    
    public StudentAppointment findOneById(Long id) {
        return studentAppointmentRepository.findOne(id);
    }
    
    /*
     * 支付失败
     */
    public Boolean payFail(Long id) {
        StudentAppointment record = studentAppointmentRepository.findOne(id);
        if (record == null) {
            return false;
        }
        record.setCourseStatus(-9);
        record.setPayStatus(-1);
        record.setUpdateTime(LocalDateTime.now());
        studentAppointmentRepository.save(record);
        return true;
    }
    
    /**
     * 预约成功
     *
     * @param id
     * @return
     */
    public Boolean paySuccess(Long id) {
        StudentAppointment record = studentAppointmentRepository.findOne(id);
        if (record == null) {
            return false;
        }
        record.setPayStatus(1);
        record.setCourseStatus(0);
        record.setUpdateTime(LocalDateTime.now());
        studentAppointmentRepository.save(record);
        // 发送短信
        User user = userService.findOne(record.getStudentId());
        if (user != null) {
            SendMessageUtils.sendSms(user.getUserMobile(), SMSEnum.appointment, new HashMap<>());
        }
        return true;
    }
    
    /**
     * 根据条件查询
     *
     * @param query
     * @param pageable
     * @return
     */
    public Page<StudentAppointmentDTO> find(AppointmentQuery query, Pageable pageable) {
        Specification<StudentAppointment> specification = getSpecification(query);
        Page<StudentAppointment> result = studentAppointmentRepository.findAll(specification, pageable);
        if (CollectionUtils.isEmpty(result.getContent())) {
            return new PageImpl<StudentAppointmentDTO>(Collections.emptyList());
        }
        // 查询ID
        List<Long> teacherIds = new ArrayList<>();
        for (StudentAppointment record : result.getContent()) {
            teacherIds.add(record.getTeacherId());
            teacherIds.add(record.getStudentId());
        }
        final Map<Long, User> map = userService.loadUserMap(teacherIds);
        // 返回结果
        return result.map(s -> {
            StudentAppointmentDTO dto = new StudentAppointmentDTO();
            BeanUtils.copyProperties(s, dto);
            User student = map.get(s.getStudentId());
            if (student != null) {
                dto.setStudentName(student.getUserNickname());
                dto.setStudentMobile(student.getUserMobile());
            }
            student = map.get(s.getTeacherId());
            if (student != null) {
                dto.setTeacherName(student.getUserNickname());
                dto.setTeacherMobile(student.getUserMobile());
            }
            return dto;
        });
    }
    
    /**
     * 更改预约状态
     *
     * @param id
     * @param status
     * @return
     */
    public boolean editStatus(Long id, Integer status) {
        StudentAppointment studentAppointment = studentAppointmentRepository.findOne(id);
        if (studentAppointment == null) {
            throw new BusinessException("预约信息已存在");
        }
        if (studentAppointment.getCourseStatus() != 0) {
            throw new BusinessException("状态不可改");
        }
        studentAppointment.setCourseStatus(status);
        studentAppointment.setUpdateTime(LocalDateTime.now());
        studentAppointmentRepository.save(studentAppointment);
        return true;
    }
    
    private Specification<StudentAppointment> getSpecification(final AppointmentQuery query) {
        return new Specification<StudentAppointment>() {
            
            @Override
            public Predicate toPredicate(Root<StudentAppointment> root, CriteriaQuery<?> criteriaQuery,
                CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList = new ArrayList<Predicate>();
                // 根据老师ID查询
                if (query.getTeacherId() != null) {
                    predicateList.add(criteriaBuilder.equal(root.get("teacherId"), query.getTeacherId()));
                }
                if (query.getUserId() != null) {
                    predicateList.add(criteriaBuilder.equal(root.get("studentId"), query.getUserId()));
                }
                // 根据预约状态查询
                if (query.getStatus() != null) {
                    predicateList.add(criteriaBuilder.equal(root.get("courseStatus"), query.getStatus()));
                }
                // 查询已支付过的
                predicateList.add(criteriaBuilder.equal(root.get("payStatus"), 1));
                Predicate[] predicates = new Predicate[predicateList.size()];
                Order order = criteriaBuilder.desc(root.get("updateTime").as(Date.class));
                return criteriaQuery.where(predicateList.toArray(predicates)).orderBy(order).getRestriction();
            }
        };
    }
}
