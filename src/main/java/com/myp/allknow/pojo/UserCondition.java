package com.myp.allknow.pojo;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class UserCondition{
    private String userName;
    private Integer sex;
    private String nickname;
    private String birthday;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date regTime;

    private String lastLoginTime;
    private Integer status;
    private Integer registrationSource;
}