package com.ald.finance.job;

import com.ald.finance.common.enums.CourseRecordEnum;
import com.ald.finance.dal.entity.StudentCourseRecord;
import com.ald.finance.dal.entity.TeacherCourseRecord;
import com.ald.finance.dal.entity.TeacherCourseRecordRel;
import com.ald.finance.dal.repository.StudentCourseRecordRepository;
import com.ald.finance.dal.repository.TeacherCourseRecordRelRepository;
import com.ald.finance.dal.repository.TeacherCourseRecordRepository;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by liang3.zhang on 2018/5/8.
 */
@Component
public class CourseRecordJob {

    private static final Logger log = LoggerFactory.getLogger(CourseRecordJob.class);

    @Autowired
    TeacherCourseRecordRepository teacherCourseRecordRepository;

    @Autowired
    TeacherCourseRecordRelRepository teacherCourseRecordRelRepository;

    @Autowired
    StudentCourseRecordRepository studentCourseRecordRepository;

    /**
     * 10分钟执行一次
     * 将课程状态更改为已上课（已请假的不做更改）
     */
    @Scheduled(cron = "0 0/1 * * * ?")
    public void changeStatus() {
        final LocalTime time = LocalTime.now();
        List<TeacherCourseRecord> list = teacherCourseRecordRepository.findAllByCourseRecordDate(LocalDate.now());
        list = list.stream().filter(s -> s.getCourseRecordStatus() == 0 && s.getCourseRecordStartTime().isBefore(time))
                .map(record -> {
                    log.info("更改上课状态,{}", JSON.toJSONString(record));
                    record.setCourseRecordStatus(CourseRecordEnum.cls.getCode());
                    return record;
                }).collect(Collectors.toList());
        teacherCourseRecordRepository.save(list);

        //更改对应的学生上课状态
        if (CollectionUtils.isEmpty(list)) return;
        List<Long> ids = list.stream().map(s -> s.getId()).collect(Collectors.toList());
        List<TeacherCourseRecordRel> recordRelList = teacherCourseRecordRelRepository.findAllByTeacherCourseRecordIdIn(ids);

        recordRelList = recordRelList.stream().filter(s -> s.getStatus() == 0).map(s -> {
            s.setStatus(1);
            return s;
        }).collect(Collectors.toList());
        teacherCourseRecordRelRepository.save(recordRelList);
        if (CollectionUtils.isEmpty(recordRelList)) return;
        ids = recordRelList.stream().map(s -> s.getStudentCourseRecordId()).collect(Collectors.toList());
        List<StudentCourseRecord> studentCourseRecords = studentCourseRecordRepository.findAll(ids);
        studentCourseRecords = studentCourseRecords.stream().filter(s -> s.getStatus() == 0).map(s -> {
            s.setStatus(1);
            return s;
        }).collect(Collectors.toList());
        studentCourseRecordRepository.save(studentCourseRecords);
    }
}
