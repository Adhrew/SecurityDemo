package com.myp.allknow.pojo;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Data
public class SimpleUser{

    @NotEmpty(message="用户名不能为空")
    @Length(min=6,max = 12,message="用户名长度必须位于6到12之间")
    private String userName;

    @NotEmpty(message="密码不能为空")
    @Length(min=6,message="密码长度不能小于6位")
    private String password;

    private Integer sex;

    private String nickname;

    private Integer registrationSource;
}