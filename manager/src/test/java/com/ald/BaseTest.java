package com.ald;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.ald.finance.JYXTAdminApplication;

/**
 * Created by liang3.zhang on 2018/5/9.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = JYXTAdminApplication.class)
@ActiveProfiles(profiles = "dev")
@WebAppConfiguration
public class BaseTest {}
