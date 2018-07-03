package com.ald.finance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Created by liang3.zhang on 2017/5/13.
 */
@SpringBootApplication
@EnableAutoConfiguration
@EnableScheduling
@ServletComponentScan
public class JYXTAdminApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(JYXTAdminApplication.class, args);
    }
}