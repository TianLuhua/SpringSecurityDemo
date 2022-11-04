package com.tlh.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

import javax.sql.DataSource;

/**
 * 创建配置类
 * 1.配置允许访问此认证服务器的客户端信息，没有再次配置的客户端信息不允许访问。
 * 2.管理令牌
 * 配置令牌管理策略(JDBC/Redis/JWT)
 * 配置令牌生成策略
 * 配置令牌端点
 * 令牌端点的安全配置
 */
@Configuration
@EnableAuthorizationServer
public class SpringAuthorizationServerConfigurer extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private DataSource dataSource;


    /**
     * 这个方法是对客户端进行配置，一个验证服务器可以预设很多个客户端，
     * 之后这些指定的客户端就可以按照下面指定的方式进行验证
     *
     * @param clients 客户端配置工具
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //数据库方式
        clients.jdbc(dataSource);
        //内存方式
//        clients
//                .inMemory()   //这里我们直接硬编码创建，当然也可以像Security那样自定义或是使用JDBC从数据库读取
//                .withClient("web")   //客户端名称，随便起就行
//                .secret(passwordEncoder.encode("654321"))      //只与客户端分享的secret，随便写，但是注意要加密
//                .resourceIds("oauth2-server")
//                .autoApprove(true)    //自动审批。授权码模式的时候，用户输完账号和密码的时候如果为true就会自动授权。如果为false则需要用户手动授权
//                .scopes("book", "user", "borrow")     //授权范围
//                .redirectUris("http://www.baidu.com/") //客户端回调地址
//                .authorizedGrantTypes("client_credentials", "password", "implicit", "authorization_code", "refresh_token")//授权模式
//              ;
    }

    /**
     * Spring Security对OAuth2提供了默认可访问端点，即URL
     * ​​   /oauth/authorize​​​:申请授权码code，涉及类​​AuthorizationEndpoint​​
     * ​​   /oauth/token​​​:获取令牌token，涉及类​​TokenEndpoint​​
     * ​​   /oauth/check_token​​​:用于资源服务器请求端点来检查令牌是否有效，涉及类​​CheckTokenEndpoint​​
     *    ​​/oauth/confirm_access​​​:用于确认授权提交，涉及类​​WhitelabelApprovalEndpoint​​
     *    ​​/oauth/error​​​:授权错误信息，涉及​​WhitelabelErrorEndpoint​​
     * ​​   /oauth/token_key​​​:提供公有密匙的端点，使用JWT令牌时会使用，涉及类​​TokenKeyEndpoint​​
     *
     * @param security 安全配置
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        security
                .passwordEncoder(passwordEncoder)    //编码器设定PasswordEncoder
                .allowFormAuthenticationForClients()  //允许客户端使用表单验证，一会我们POST请求中会携带表单信息
                .checkTokenAccess("permitAll()");     //允许所有的Token查询请求
    }

    /**
     * @param endpoints
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        //密码模式需要配置认证管理器
        endpoints.authenticationManager(authenticationManager);
        //刷新令牌获取新令牌时需要
        endpoints.userDetailsService(userDetailsService);
        //令牌管理策略
        endpoints.tokenStore(tokenStore);
        //授权码管理策略，针对授权码模式有效，会将授权码放到 auth_code 表，授权后就会删除它
//        endpoints.authorizationCodeServices(jdbcAuthorizationCodeServices);
    }

}
