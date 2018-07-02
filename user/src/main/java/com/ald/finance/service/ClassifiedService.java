package com.ald.finance.service;

import com.ald.finance.common.enums.CourseRecordEnum;
import com.ald.finance.common.utils.MyDateUtils;
import com.ald.finance.common.web.BusinessException;
import com.ald.finance.dal.entity.*;
import com.ald.finance.dal.repository.*;
import com.ald.finance.service.dto.TeacherCourseRecordDTO;
import com.ald.finance.service.query.TeacherCourseRecordQuery;
import com.ald.finance.web.reset.vm.ClassifiedApplyVm;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by liang3.zhang on 2018/5/8.
 */
@Service
public class ClassifiedService {
    
    @Autowired
    TeacherCourseRecordRelRepository teacherCourseRecordRelRepository;
    
    @Autowired
    TeacherCourseRecordService teacherCourseRecordService;
    
    @Autowired
    HolidayRepository holidayRepository;
    
    @Autowired
    UserRepository teacherRepository;
    
    @Autowired
    StudentBuyRecordRepository studentBuyRecordRepository;
    
    @Autowired
    PackagesRepository packagesRepository;
    
    @Autowired
    TeacherCourseRepository teacherCourseRepository;
    
    @Autowired
    StudentCourseRecordRepository studentCourseRecordRepository;
    
    // 提交购买记录
    @Transactional
    public StudentBuyRecord buy(User user, ClassifiedApplyVm vm) {
        Date date = MyDateUtils.toDate(vm.getCourseDate());
        if (date == null) {
            throw new BusinessException("时间格式错误");
        }
        // 判定老师是否存在
        User teacher = teacherRepository.findOne(vm.getTeacherId());
        if (teacher == null) {
            throw new BusinessException("老师不存在");
        }
        // 判断是否选择了套餐
        Packages packages;
        if (vm.getPackageId() == null) {
            throw new BusinessException("请选择套餐");
        }
        packages = packagesRepository.findOne(vm.getPackageId());
        if (packages == null) {
            throw new BusinessException("套餐不存在");
        }
        
        // 查询老师的课程
        List<TeacherCourse> list = teacherCourseRepository.findAllByUserId(vm.getTeacherId());
        Map<Long, TeacherCourse> courseMap = new ConcurrentHashMap<>();
        for (TeacherCourse course : list) {
            courseMap.put(course.getId(), course);
        }
        
        // 查询节假日
        List<Holiday> holidays = holidayRepository.findAll();
        Map<LocalDate, Holiday> holidayMap = new ConcurrentHashMap<>();
        for (Holiday holiday : holidays) {
            holidayMap.put(holiday.getHolidayDay(), holiday);
        }
        
        // 查询课程ID列表
        List<Long> courseIds = new ArrayList<>();
        List<String> keys = vm.getCourseId();
        for (String key : keys) {
            String courseId = key.split("_")[2];
            courseIds.add(Long.parseLong(courseId));
        }
        
        // 保存数据库
        StudentBuyRecord studentBuyRecord = new StudentBuyRecord();
        studentBuyRecord.setUserId(user.getId());
        studentBuyRecord.setTeacherId(vm.getTeacherId());
        studentBuyRecord.setPackageId(vm.getPackageId());
        studentBuyRecord.setStatus(0);
        studentBuyRecord.setCourseIds(courseIds.toString());
        studentBuyRecord.setPackageIndex(packages.getIndexs());
        studentBuyRecord.setPackagePrice(packages.getPrice());
        studentBuyRecord.setPackageTimes(packages.getTimes());
        studentBuyRecord.setPackageType(packages.getType());
        studentBuyRecord.setCreateTime(LocalDateTime.now());
        studentBuyRecord.setUpdateTime(LocalDateTime.now());
        studentBuyRecord.setStartDate(LocalDate.parse(vm.getCourseDate()));
        studentBuyRecord.setStudentIndex(0L);
        studentBuyRecord = studentBuyRecordRepository.save(studentBuyRecord);
        // 遍历课程
        
        LocalDate localDate = LocalDate.parse(vm.getCourseDate());
        int current = 0;
        
        int week = localDate.getDayOfWeek().getValue();
        Long courseId1 = courseIds.get(0);
        do {
            for (Long courseId : courseIds) {
                // 判断老师端课程是否被占用
                TeacherCourseRecordQuery query = new TeacherCourseRecordQuery();
                query.setUserId(vm.getTeacherId());
                query.setCourseId(courseId);
                query.setCourseDate(localDate);
                TeacherCourseRecord teacherCourseRecord = findOneWithTeacherCourse(query, packages, courseMap,
                    holidayMap, teacher);
                // 创建学生课程
                StudentCourseRecord studentCourseRecord = studentCourseRecordRepository
                    .findOneByCourseDateAndUserIdAndCourseIndex(localDate, user.getId(),
                        teacherCourseRecord.getCourseRecordIndex());
                if (studentCourseRecord != null) {
                    throw new BusinessException("重复购买");
                }
                studentCourseRecord = new StudentCourseRecord();
                studentCourseRecord.setUserId(user.getId());
                studentCourseRecord.setBuyRecordId(studentBuyRecord.getId());
                studentCourseRecord.setCourseDate(localDate);
                studentCourseRecord.setCreateTime(LocalDateTime.now());
                studentCourseRecord.setCourseId(teacherCourseRecord.getCourseId());
                studentCourseRecord.setCourseStartTime(teacherCourseRecord.getCourseRecordStartTime());
                studentCourseRecord.setCourseEndTime(teacherCourseRecord.getCourseRecordEndTime());
                studentCourseRecord.setCourseIndex(teacherCourseRecord.getCourseRecordIndex());
                studentCourseRecord.setUpdateTime(LocalDateTime.now());
                studentCourseRecord.setStatus(teacherCourseRecord.getCourseRecordStatus());
                studentCourseRecord = studentCourseRecordRepository.save(studentCourseRecord);
                // 建立映射关系
                TeacherCourseRecordRel recordRel = new TeacherCourseRecordRel();
                recordRel.setStatus(teacherCourseRecord.getCourseRecordStatus());
                recordRel.setStudentCourseRecordId(studentCourseRecord.getId());
                recordRel.setTeacherCourseRecordId(teacherCourseRecord.getId());
                teacherCourseRecordRelRepository.save(recordRel);
                // 上课节次加1
                if (teacherCourseRecord.getCourseRecordStatus() == 0) {
                    current = current + 1;
                }
                // 不是同一个星期的
                if (localDate.getDayOfWeek().getValue() != week) {
                    localDate = localDate.plusDays(1);
                    week = localDate.getDayOfWeek().getValue();
                }
                // 同一个星期的
                if (courseId1.equals(courseId)) {
                    localDate = localDate.plusDays(1);
                    week = localDate.getDayOfWeek().getValue();
                }
            }
        }
        while (current > packages.getIndexs());
        return studentBuyRecord;
    }
    
    // 获取老师的课程
    private TeacherCourseRecord findOneWithTeacherCourse(TeacherCourseRecordQuery query, Packages packages,
        Map<Long, TeacherCourse> courseMap, Map<LocalDate, Holiday> holidayMap, User teacher) {
        
        Page<TeacherCourseRecordDTO> page = teacherCourseRecordService.find(query, new PageRequest(0, 1));
        List<TeacherCourseRecordDTO> list = page.getContent();
        // 没有被占用、则新建
        if (CollectionUtils.isEmpty(list)) {
            TeacherCourseRecord record = new TeacherCourseRecord();
            record.setCreateTime(LocalDateTime.now());
            record.setUpdateTime(LocalDateTime.now());
            record.setCurrent(1);
            record.setMax(packages.getType());
            record.setCourseId(query.getCourseId());
            record.setUserId(query.getUserId());
            record.setCourseRecordDate(query.getCourseDate());
            TeacherCourse teacherCourse = courseMap.get(query.getCourseId());
            record.setCourseRecordIndex(teacherCourse.getCourseIndex());
            record.setCourseRecordTimes(teacherCourse.getTimes());
            record.setCourseRecordStartTime(teacherCourse.getStartTime());
            record.setCourseRecordEndTime(teacherCourse.getEndTime());
            record.setCourseRecordPrice(teacher.getUserPrice());
            record.setCourseRecordStatus(holidayMap.containsKey(query.getCourseDate())
                ? CourseRecordEnum.holiday.getCode() : CourseRecordEnum.none.getCode());
            return teacherCourseRecordService.teacherCourseRecordRepository.save(record);
        }
        // 已存在课程、则人数增加1
        TeacherCourseRecordDTO dto = page.getContent().get(0);
        TeacherCourseRecord record = new TeacherCourseRecord();
        BeanUtils.copyProperties(dto, record);
        if (packages.getType() < record.getCurrent()) {
            throw new BusinessException("课程已被占用");
        }
        if (record.getCurrent() < record.getMax()) {
            record.setCurrent(record.getCurrent() + 1);
            // 取最小的数据
            record.setMax(record.getMax() > packages.getType() ? packages.getType() : record.getMax());
            return teacherCourseRecordService.teacherCourseRecordRepository.save(record);
        }
        throw new BusinessException("课程已被占用");
    }
}
