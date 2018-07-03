package com.ald.finance.job;

import com.ald.finance.dal.entity.TeacherPayRecord;
import com.ald.finance.dal.repository.TeacherPayRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangliang on 2018/6/3.
 */
@Component
public class TeacherPayRecordJob {
    @Autowired
    TeacherPayRecordRepository teacherPayRecordRepository;

    @Autowired
    EntityManager entityManager;

    /**
     * 同步账单
     * 每天执行一次、23点执行
     */
    @Scheduled(cron = "0 0 23 1/1 * ?")
    public void pay() {
        LocalDate localDate = LocalDate.now();
        // 聚合查询每个老师对应的今天上课费用
        String sql = "select sum(course_record_price) as pay,user_id from course_record where course_record_status = 1 and course_record_price_flag=0 and course_day = ? group BY user_id";
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter(1, localDate.toString());
        List<Object[]> list = query.getResultList();
        List<TeacherPayRecord> temp = new ArrayList<>();
        for (Object[] obj : list) {
            Double d = (Double) obj[0];
            Integer teacherId = (Integer) obj[1];
            if (d == 0)
                continue;
            TeacherPayRecord payRecord = teacherPayRecordRepository.findOneByUserIdAndYearAndMonth((long) teacherId, localDate.getYear(),
                    localDate.getMonth().getValue());
            if (payRecord == null) {
                payRecord = new TeacherPayRecord();
                payRecord.setPrice(0D);
                payRecord.setStatus(0);
                payRecord.setUserId((long) teacherId);
                payRecord.setMonth(localDate.getMonth().getValue());
                payRecord.setYear(localDate.getYear());
                payRecord.setCreateTime(LocalDateTime.now());
            }
            payRecord.setUpdateTime(LocalDateTime.now());
            payRecord.setPrice(payRecord.getPrice() + d);
            temp.add(payRecord);
        }
        if (!CollectionUtils.isEmpty(temp)) {
            teacherPayRecordRepository.save(temp);
        }
    }
}
