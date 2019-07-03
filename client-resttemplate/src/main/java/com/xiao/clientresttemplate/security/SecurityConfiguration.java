package com.xiao.clientresttemplate.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    /**
     * index.html 和 / 是允许匿名访问的
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests() //启动httpBasic登录身份认证
                .antMatchers("/", "/index.html", "myLogin.html").permitAll() //这些页面不需要认证
                .anyRequest().authenticated()  //所有请求都需要认证
                .and()
                .formLogin().loginPage("/myLogin").loginProcessingUrl("/login").permitAll() //自定义登录页面
                .and().logout().permitAll() //登录和登出不需要认证
                .and()
                .csrf().disable(); //禁用跨站攻击
    }

    /**
     * 解决 id null 问题
     *
     * @return
     */
    @Bean
    public static NoOpPasswordEncoder passwordEncoder() {
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }


}
