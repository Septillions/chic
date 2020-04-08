package com.github.chic.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.github.chic"})
@MapperScan("com.github.chic.admin.mapper")
public class ChicAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChicAdminApplication.class, args);
    }

}
