package com.tlh.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.redis.connection.RedisConnectionFactory;

@SpringBootApplication
public class SecurityAuthServiceApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(SecurityAuthServiceApplication.class, args);
        RedisConnectionFactory bean = run.getBean(RedisConnectionFactory.class);
    }

}
