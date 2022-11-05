package security.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;

@Configuration
public class TLHOauthConfig {

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
    public TokenStore jdbcTokenStore(DataSource dataSource) {
        return new JdbcTokenStore(dataSource);
    }

    /**
     * 配置资源服务器如何校验token
     * 1. DefaultTokenServices
     * 如果认证服务器和资源服务器在同一个服务，则直接采用默认服务验证
     * 2.RemoteTokenServices
     * 当认证服务器和资源服务器不在同一个服务，要使用此服务器去远程认证服务器验证
     *
     * @return
     */
    @Bean
    @Primary
    public RemoteTokenServices tokenServices() {
        //资源服务器去远程认证服务器验证 token 是否有效
        final RemoteTokenServices tokenService = new RemoteTokenServices();
        //请求认证服务器验证URL，注意：默认这个端点是拒绝访问的，要设置认证后可访问
        tokenService.setCheckTokenEndpointUrl("http://localhost:8081/oauth/check_token");
        //在认证服务器配置的客户端id
        tokenService.setClientId("test-pc");
        //在认证服务器配置的客户端密码
        tokenService.setClientSecret("123456");
        return tokenService;
    }

}
