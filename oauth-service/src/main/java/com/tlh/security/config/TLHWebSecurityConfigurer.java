package com.tlh.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 创建安全配置类
 * 指定认证用户的用户名和密码，用户和密码是资源的所有者。
 * 这个用户名和密码和客户端id和密码是不一样的，客户端ID和密码是应用系统的标识，每个应用系统对应一个客户端id和密码。
 */
@EnableWebSecurity
public class TLHWebSecurityConfigurer extends WebSecurityConfigurerAdapter {


    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .anyRequest().authenticated()  //
                .and()
                .formLogin().permitAll();    //使用表单登录
    }

    /**
     * 认证管理器
     * 1.认证信息提供方式(用户名、密码、当前用户的资源权限)
     * 2.可采用内存存储方式，也可能采用数据库方式等
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //数据库方式
        auth.userDetailsService(userDetailsService);
//        //内存方式
//        auth.inMemoryAuthentication()
//                .withUser("admin")
//                .password(passwordEncoder.encode("123456"))
//                .roles("admin")
//                .authorities("Role_admin")
//                .and()
//                .passwordEncoder(passwordEncoder);//配置加密方式
    }

    /**
     * password 密码模式要使用此认证管理器
     *
     * @return
     * @throws Exception
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
