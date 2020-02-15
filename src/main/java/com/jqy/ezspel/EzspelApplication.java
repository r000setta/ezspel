package com.jqy.ezspel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableMBeanExport;

@SpringBootApplication
@EnableMBeanExport
public class EzspelApplication {

    public static void main(String[] args) {
        SpringApplication.run(EzspelApplication.class, args);
    }

}
