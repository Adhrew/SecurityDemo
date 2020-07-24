package com.myp.allknow.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myp.allknow.configure.RsaKeyProperties;
import com.myp.allknow.pojo.LoginUser;
import com.myp.allknow.pojo.UserRole;
import com.myp.allknow.utils.JwtUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;
    private RsaKeyProperties prop;

    public JwtLoginFilter(AuthenticationManager authenticationManager, RsaKeyProperties prop) {
        this.authenticationManager = authenticationManager;
        this.prop = prop;
    }

    //重写springsecurity获取用户名和密码操作
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            //从输入流中获取用户名和密码，而不是表单
            LoginUser sysUser = new ObjectMapper().readValue(request.getInputStream(), LoginUser.class);
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(sysUser.getUsername(), sysUser.getPassword());
            return authenticationManager.authenticate(authRequest);
        } catch (Exception e) {
            try {
                //处理失败请求
                response.setContentType("application/json;charset=utf-8");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                ServletOutputStream out = response.getOutputStream();
                Map map = new HashMap<>();
                map.put("code", HttpServletResponse.SC_UNAUTHORIZED);
                map.put("msg", "用户名或者密码错误");
                out.write(new ObjectMapper().writeValueAsString(map).getBytes());
                out.flush();
                out.close();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            return null;
//            throw new RuntimeException(e); //抛出异常
        }
    }

    //重写用户名密码授权成功操作----返回token凭证
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        //从authResult获取认证成功的用户信息
        LoginUser resultUser = new LoginUser();
        LoginUser authUser = (LoginUser) authResult.getPrincipal();
        resultUser.setUsername(authUser.getUsername());
        resultUser.setUserId(authUser.getUserId());
        resultUser.setStatus(authUser.getStatus());
        resultUser.setRoles((List<UserRole>) authResult.getAuthorities());
        String token = JwtUtils.generateTokenExpireInMinutes(resultUser, prop.getPrivateKey(), 60*24);
        //将token写入header
        response.addHeader("Authorization", "Bearer " + token);
        try {
            //登录成功時，返回json格式进行提示
            response.setContentType("application/json;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_OK);
            ServletOutputStream out = response.getOutputStream();
            Map<String, Object> map = new HashMap<>();
            map.put("code", HttpServletResponse.SC_OK);
            map.put("message", "登陆成功！");
            out.write(new ObjectMapper().writeValueAsString(map).getBytes());
            out.flush();
            out.close();
        } catch (Exception e1) {
            e1.printStackTrace();
        }

    }
}