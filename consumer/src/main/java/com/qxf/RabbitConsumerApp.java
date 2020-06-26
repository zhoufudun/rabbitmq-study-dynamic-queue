package com.qxf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @ClassName RabbitApp
 * @Description TODO
 * @Author qiuxinfa
 * @Date 2020/6/25 9:43
 **/
@SpringBootApplication
public class RabbitConsumerApp {
    public static void main(String[] args) {
        SpringApplication.run(RabbitConsumerApp.class,args);
    }
}
