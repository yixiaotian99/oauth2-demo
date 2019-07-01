package com.xiao.passwordserver.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

/**
 * @Author sunjinwei
 * @Date 2019-06-20 23:16
 * @Description 授权服务器配置
 **/
@Configuration
@EnableAuthorizationServer
public class OAuth2AuthorizationServer extends
        AuthorizationServerConfigurerAdapter {


    /**
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients)
            throws Exception {
        clients.inMemory()  //使用内存存储
                .withClient("myclient")   //分配 client_id
                .secret("123")        //分配 client 的密码
                .redirectUris("http://localhost:9001/callback") //拿到授权码之后如何跳转回客户端

                // 密码模式 存储在 application.properties 中
                .authorizedGrantTypes("password")
                .scopes("read_userinfo", "read_contacts");  //允许的授权范围
    }

}
