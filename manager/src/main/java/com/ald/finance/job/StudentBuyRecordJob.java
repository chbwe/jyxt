package com.ald.finance.job;

import com.ald.finance.dal.entity.StudentBuyRecord;
import com.ald.finance.dal.repository.StudentBuyRecordRepository;
import com.ald.finance.service.StudentBuyRecordService;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by zhangliang on 2018/6/3.
 */
@Component
public class StudentBuyRecordJob {

    private static final Logger log = LoggerFactory.getLogger(StudentBuyRecordJob.class);

    @Autowired
    StudentBuyRecordRepository studentBuyRecordRepository;

    @Autowired
    StudentBuyRecordService studentBuyRecordService;

    @Scheduled(cron = "0 0/1 * * * ?")
    public void closePay() {
        List<StudentBuyRecord> records = studentBuyRecordRepository.findAllByStatus(0);
        LocalDateTime localDateTime = LocalDateTime.now().minusMinutes(30);
        for (StudentBuyRecord record : records) {
            //讲状态设置成未付款
            if (record.getCreateTime().isBefore(localDateTime)) {
                log.info("超过15分钟未支付,关闭交易记录.{}", JSON.toJSONString(record));
                studentBuyRecordService.payFail(record.getId());
            }
        }
    }
}
