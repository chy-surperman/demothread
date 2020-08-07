package com;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(value = {"com.chy.*.mappers","com.*.mappers"})
public class StartProject {
    public static void main(String[] args) {
        SpringApplication.run(StartProject.class,args);
    }
}
