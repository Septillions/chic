package com.github.chic.app;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = "com.github.chic")
@MapperScan({"com.github.chic.mapper", "com.github.chic.app.dao"})
@EnableTransactionManagement
public class ChicAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChicAppApplication.class, args);
    }

}
