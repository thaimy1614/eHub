package com.datn.user_service;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableFeignClients
@EnableKafka
public class UserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

    @Bean
    NewTopic sendOtp() {
        return new NewTopic("sendOTP", 3, (short) 1);
    }

    @Bean
    NewTopic sendNewPassword() {
        return new NewTopic("sendNewPassword", 3, (short) 1);
    }

    @Bean
    NewTopic sendVerification() {
        return new NewTopic("verification", 3, (short) 1);
    }

    @Bean
    NewTopic sendAccountInfo() {
        return new NewTopic("sendAccountInfo", 3, (short) 1);
    }
}
