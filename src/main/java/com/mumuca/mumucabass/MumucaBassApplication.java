package com.mumuca.mumucabass;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableCaching
@EnableRabbit
public class MumucaBassApplication {
    public static void main(String[] args) {
        SpringApplication.run(MumucaBassApplication.class, args);
    }
}
