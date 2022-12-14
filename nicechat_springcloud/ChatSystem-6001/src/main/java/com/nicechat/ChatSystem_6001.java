package com.nicechat;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//启动类
@SpringBootApplication
@MapperScan("com.nicechat.dao")
public class ChatSystem_6001 {

    public static void main(String[] args) {
        SpringApplication.run(ChatSystem_6001.class, args);
        System.out.println("ChatSystem-6001 success!");
    }
}
