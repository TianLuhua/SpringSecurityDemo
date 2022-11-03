package com.tlh.security.config;

import com.tlh.security.SpringAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SpringWebSecurityConfigurer extends WebSecurityConfigurerAdapter {

    @Autowired
    private SpringAuthenticationProvider authenticationProvider;

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

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        //数据库的形式
        auth.authenticationProvider(authenticationProvider);

        //内存的形式
        auth
                .inMemoryAuthentication()   //直接创建一个用户，懒得搞数据库了
                .passwordEncoder(passwordEncoder)
                .withUser("test").password("123456").roles("USER");
    }

    /**
     * 这里需要将AuthenticationManager注册为Bean，在OAuth配置中使用
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
