package com.ald.finance.service;

import com.ald.finance.common.enums.CourseRecordEnum;
import com.ald.finance.common.enums.SMSEnum;
import com.ald.finance.common.utils.SendMessageUtils;
import com.ald.finance.dal.entity.*;
import com.ald.finance.dal.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Created by zhangliang on 2018/6/3.
 */
@Service
public class SysStudentCourseService {
    
    @Autowired
    UserRepository userRepository;
    
    @Autowired
    StudentStopRecordRepository studentStopRecordRepository;
    
    @Autowired
    StudentCourseRecordRepository studentCourseRecordRepository;
    
    @Autowired
    TeacherCourseRecordRelRepository teacherCourseRecordRelRepository;
    
    @Autowired
    TeacherCourseRecordRepository teacherCourseRecordRepository;
    
    /**
     * 更改老师端上课人数
     *
     * @param ids
     * @return
     */
    public Set<Long> updateTeacherCurrent(List<Long> ids) {
        if (CollectionUtils.isEmpty(ids))
            return null;
        List<TeacherCourseRecordRel> teacherCourseRecordRels = teacherCourseRecordRelRepository
            .findAllByStudentCourseRecordIdIn(ids);
        ids = teacherCourseRecordRels.stream().map(s -> s.getTeacherCourseRecordId()).collect(Collectors.toList());
        // 查询对应的老师课程列表
        Set<Long> set = new HashSet<>();
        List<TeacherCourseRecord> teacherCourseRecords = teacherCourseRecordRepository.findAll(ids);
        for (TeacherCourseRecord teacherCourseRecord : teacherCourseRecords) {
            if (teacherCourseRecord.getCourseRecordStatus() == 0) {
                teacherCourseRecord.setCurrent(teacherCourseRecord.getCurrent() + 1);
                if (teacherCourseRecord.getMax() == 1) {
                    teacherCourseRecord.setMax(3);
                }
                set.add(teacherCourseRecord.getUserId());
            }
        }
        teacherCourseRecordRepository.save(teacherCourseRecords);
        return set;
    }
    
    // 购买支付失败，则删除购买课程记录
    public void payFail(StudentBuyRecord record) {
        List<StudentCourseRecord> list = studentCourseRecordRepository.findAllByBuyRecordId(record.getId());
        studentCourseRecordRepository.delete(list);
        // 更改教师端上课人数
        List<Long> temps = list.stream().map(s -> s.getId()).collect(Collectors.toList());
        updateTeacherCurrent(temps);
    }
    
    /***
     * 学生请假
     *
     * @param record
     */
    public void studentLeave(StudentLeaveRecord record) {
        StudentCourseRecord studentCourseRecord = studentCourseRecordRepository.findOne(record.getUserId());
        if (studentCourseRecord == null || !studentCourseRecord.getStatus().equals(CourseRecordEnum.none.getCode()))
            return;
        
        studentCourseRecord.setStatus(CourseRecordEnum.leave.getCode());
        studentCourseRecord.setUpdateTime(LocalDateTime.now());
        studentCourseRecordRepository.save(studentCourseRecord);
        
        // 更改老师端上课人数
        List<Long> ids = new ArrayList<>();
        ids.add(studentCourseRecord.getId());
        Set<Long> userIds = updateTeacherCurrent(ids);
        
        // 发送短信
        if (userIds == null || CollectionUtils.isEmpty(userIds))
            return;
        User users = userRepository.findOne(record.getUserId());
        Map<String, String> map = new ConcurrentHashMap<>();
        map.put("userName", users != null ? users.getUserNickname() : "");
        map.put("date", record.getDate().toString());
        sendMessage(map, SMSEnum.leave, userIds);
    }
    
    /**
     * 学生停课
     *
     * @param stopRecord
     */
    public void studentStop(StudentStopRecord stopRecord) {
        List<StudentCourseRecord> studentStopRecords = studentCourseRecordRepository
            .findAllByUserId(stopRecord.getUserId());
        List<StudentCourseRecord> temp = new ArrayList<>();
        List<Long> ids = new ArrayList<>();
        for (StudentCourseRecord studentCourseRecord : studentStopRecords) {
            if (studentCourseRecord.getCourseDate().isBefore(stopRecord.getStartTime())
                && studentCourseRecord.getCourseDate().isAfter(stopRecord.getEndTime())) {
                ids.add(studentCourseRecord.getId());
                studentCourseRecord.setStatus(CourseRecordEnum.stop.getCode());
                studentCourseRecord.setUpdateTime(LocalDateTime.now());
                temp.add(studentCourseRecord);
            }
        }
        studentCourseRecordRepository.save(temp);
        // 更改老师端上课人数
        Set<Long> userIds = updateTeacherCurrent(ids);
        // 发送短信
        if (userIds == null || CollectionUtils.isEmpty(userIds))
            return;
        User users = userRepository.findOne(stopRecord.getUserId());
        Map<String, String> map = new ConcurrentHashMap<>();
        map.put("userName", users != null ? users.getUserNickname() : "");
        map.put("start", stopRecord.getStartTime().toString());
        map.put("end", stopRecord.getEndTime().toString());
        sendMessage(map, SMSEnum.stop, userIds);
    }
    
    /**
     * 老师更改课程时间
     *
     * @param teacherCourse
     */
    public void changeCourse(TeacherCourse teacherCourse) {
        List<TeacherCourseRecord> list = teacherCourseRecordRepository.findAllByCourseId(teacherCourse.getId());
        // 过滤掉今天以前的时间
        list = list.stream().filter(s -> s.getCourseRecordDate().isBefore(LocalDate.now()))
            .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(list))
            return;
        List<Long> ids = new ArrayList<>();
        for (TeacherCourseRecord record : list) {
            record.setCourseRecordStartTime(teacherCourse.getStartTime());
            record.setCourseRecordEndTime(teacherCourse.getEndTime());
            record.setUpdateTime(LocalDateTime.now());
            ids.add(record.getId());
        }
        teacherCourseRecordRepository.save(list);
        // 查询学生上课记录
        List<TeacherCourseRecordRel> temp = teacherCourseRecordRelRepository.findAllByTeacherCourseRecordIdIn(ids);
        ids = temp.stream().map(s -> s.getStudentCourseRecordId()).collect(Collectors.toList());
        
        List<StudentCourseRecord> list1 = studentCourseRecordRepository.findAll(ids);
        Set<Long> teacherIds = new HashSet<>();
        for (StudentCourseRecord record : list1) {
            record.setCourseStartTime(teacherCourse.getStartTime());
            record.setCourseEndTime(teacherCourse.getEndTime());
            record.setUpdateTime(LocalDateTime.now());
            teacherIds.add(record.getUserId());
        }
        studentCourseRecordRepository.save(list1);
        
        // 发送消息
        User teacher = userRepository.findOne(teacherCourse.getUserId());
        Map<String, String> map = new ConcurrentHashMap<>();
        map.put("userName", teacher != null ? teacher.getUserNickname() : "");
        map.put("start", teacherCourse.getStartTime().toString());
        map.put("end", teacherCourse.getEndTime().toString());
        sendMessage(map, SMSEnum.change, teacherIds);
    }
    
    public void sendMessage(Map<String, String> map, SMSEnum smsEnum, Set<Long> teacherIds) {
        List<User> users = userRepository.findAll(teacherIds);
        if (CollectionUtils.isEmpty(users))
            return;
        for (User user : users) {
            SendMessageUtils.sendSms(user.getUserMobile(), smsEnum, map);
        }
    }
}
