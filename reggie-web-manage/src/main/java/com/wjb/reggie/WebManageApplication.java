package com.wjb.reggie;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan("com.wjb.reggie.mapper")
@Slf4j
@EnableTransactionManagement
public class WebManageApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebManageApplication.class,args);
        log.info("项目启动成功...");
    }
}
