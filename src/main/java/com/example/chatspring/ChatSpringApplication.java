package com.example.chatspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class ChatSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChatSpringApplication.class, args);
    }

}
