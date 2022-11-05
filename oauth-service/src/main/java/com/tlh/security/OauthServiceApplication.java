package com.tlh.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.redis.connection.RedisConnectionFactory;

@SpringBootApplication
public class OauthServiceApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(OauthServiceApplication.class, args);
        RedisConnectionFactory bean = run.getBean(RedisConnectionFactory.class);
    }

}
