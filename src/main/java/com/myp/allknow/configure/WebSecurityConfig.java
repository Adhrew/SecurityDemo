package com.myp.allknow.configure;

import com.myp.allknow.filter.JwtLoginFilter;
import com.myp.allknow.filter.JwtVerifyFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private DbUserDetailsService dbUserDetailsService;
    private RsaKeyProperties prop;

    @Autowired
    public void setDbUserDetailsService(DbUserDetailsService dbUserDetailsService, RsaKeyProperties prop, BCryptPasswordEncoder bCryptPasswordEncoder){
        this.dbUserDetailsService = dbUserDetailsService;
        this.prop = prop;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    /**
     * 匹配 "/" 路径，不需要权限即可访问
     * 匹配 "/user" 及其以下所有路径，都需要 "USER" 权限
     * 登录地址为 "/login"，登录成功默认跳转到页面 "/user"
     * 退出登录的地址为 "/logout"，退出成功后跳转到页面 "/login"
     * 默认启用 CSRF
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //关闭跨站请求防护
                .cors()
                .and()
//                .csrf()
//                .disable()
                //允许不登陆就可以访问的方法，多个用逗号分隔
                .authorizeRequests()
                .antMatchers("/swagger-ui.html","/webjars/**","/swagger-resources","/swagger-resources/**","/v2/api-docs").permitAll()
                //其他的需要登陆授权后访问
                .anyRequest().authenticated()
                .and()
                .anonymous().authorities("ROLE_ANONYMOUS").and()
                //增加自定义认证过滤器
                .addFilter(new JwtLoginFilter(authenticationManager(), prop))
                //增加自定义验证认证过滤器
                .addFilter(new JwtVerifyFilter(authenticationManager(), prop))
                // 前后端分离是无状态的，不用session了，直接禁用。
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    /**
     * 添加 UserDetailsService， 实现自定义登录校验
     */
    @Override
    protected void configure(AuthenticationManagerBuilder builder) throws Exception{
        builder.userDetailsService(dbUserDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}