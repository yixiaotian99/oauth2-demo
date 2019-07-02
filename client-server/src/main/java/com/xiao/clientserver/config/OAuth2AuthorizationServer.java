package com.xiao.clientserver.config;

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
                // 授权码模式
                .authorizedGrantTypes("client_credentials")
                .scopes("devops");  //允许的授权范围
    }

}
