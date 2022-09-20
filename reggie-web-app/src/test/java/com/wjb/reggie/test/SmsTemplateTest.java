package com.wjb.reggie.test;

import com.wjb.reggie.common.SmsTemplate;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileNotFoundException;

@SpringBootTest
@Slf4j
public class SmsTemplateTest {
    @Autowired
    private SmsTemplate smsTemplate;

    @Test
    public void testFileUpload(){
        smsTemplate.sendSms("17776677606", "123456");
    }
}