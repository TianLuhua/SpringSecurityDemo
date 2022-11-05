package com.tlh.security.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;

@Configuration
public class TLHOauthConfig {


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return String.valueOf(rawPassword);
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return String.valueOf(rawPassword).equals(encodedPassword);
            }
        };
    }

    /**
     * druid数据源
     *
     * @return 数据源
     */
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource druidDataSource() {
        return new DruidDataSource();
    }

    /**
     * jdbc管理令牌
     * 步骤：
     * 1.创建相关表
     * 2.添加jdbc相关依赖
     * 3.配置数据源信息
     *
     * @return token存储策略
     */
    @Bean
    public TokenStore jdbcTokenStore() {
        return new JdbcTokenStore(druidDataSource());
    }

//    /**
//     * Redis令牌管理
//     * 步骤：
//     * 1.启动redis
//     * 2.添加redis依赖
//     * 3.添加redis 依赖后, 容器就会有 RedisConnectionFactory 实例
//     *
//     * @return token存储策略
//     */
//    @Bean
//    public TokenStore redisTokenStore(RedisConnectionFactory redisConnectionFactory) {
//        return new RedisTokenStore(redisConnectionFactory);
//    }

}
