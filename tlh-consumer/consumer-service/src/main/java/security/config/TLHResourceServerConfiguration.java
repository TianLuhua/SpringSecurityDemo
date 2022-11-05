package security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

@Configuration
@EnableResourceServer// 标识为资源服务器, 所有发往当前服务的请求，都会去请求头里找token，找不到或 验证不通过不允许访问
@EnableGlobalMethodSecurity(prePostEnabled = true)//开启方法级权限控制
public class TLHResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    @Autowired
    public ResourceServerTokenServices tokenServices;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .requestMatchers()
                .antMatchers("/test/**");

    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId("oauth2-resource")
                .tokenServices(tokenServices);
    }

}