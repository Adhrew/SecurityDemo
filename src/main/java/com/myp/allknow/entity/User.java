package com.myp.allknow.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import java.util.Date;

@Data
@NoArgsConstructor
public class User {

    @TableId(type= IdType.AUTO)
    @NonNull private Integer userId;

    @NotEmpty(message="用户名不能为空")
    @Length(min=6,max = 12,message="用户名长度必须位于6到12之间")
    private String userName;

    @NonNull private Integer sex;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @Past
    @NonNull private String birthday;

    @NonNull private String nickname;

    @NonNull @Email(message = "请输入正确的邮箱") private String email;

    @NonNull private String mobile;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @Past
    private Date regTime;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @Past
    @NonNull private String emailIdentime;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @Past
    @NonNull private String mobileIdentime;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @Past
    @NonNull private String lastLoginTime;

    @NonNull private Integer status;
    @NonNull private Integer registrationSource;
}
