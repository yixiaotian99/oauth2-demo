package com.xiao.jwtauthserver.oauth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @Author sunjinwei
 * @Date 2019-06-20 23:57
 * @Description 解决登录页面不显示直接报错问题
 * @see https://juejin.im/post/5ae5c06051882567137df8c9
 **/
@Configuration
@Order(1)
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        AuthenticationManager manager = super.authenticationManagerBean();
        return manager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
//                .formLogin().and()
                .httpBasic().and()
                .antMatcher("/oauth/token").
                .csrf().disable();
    }


}