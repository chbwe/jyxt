package com.ald.job;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ald.BaseTest;
import com.ald.finance.service.StudentBuyRecordService;

/**
 * Created by liang3.zhang on 2018/6/4.
 */
public class BuyRecordServiceTest extends BaseTest {

    @Autowired
    StudentBuyRecordService studentBuyRecordService;

    @Test
    public  void test(){
        studentBuyRecordService.paySuccess(2L);
    }
}
