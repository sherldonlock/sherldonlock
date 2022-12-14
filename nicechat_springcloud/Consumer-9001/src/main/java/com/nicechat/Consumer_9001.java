package com.nicechat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(basePackages = {"com.nicechat"})
public class Consumer_9001 {
    public static void main(String[] args) {
        SpringApplication.run(Consumer_9001.class, args);
        System.out.println("Consumer-9001 success!");
    }
}
