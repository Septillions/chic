package com.github.chic.portal;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.github.chic.portal.mapper")
public class ChicPortalApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChicPortalApplication.class, args);
    }

}
