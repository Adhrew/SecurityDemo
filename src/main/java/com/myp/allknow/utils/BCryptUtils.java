package com.myp.allknow.utils;

import com.myp.allknow.pojo.SimpleUser;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptUtils {
    public static void encryptPassword(SimpleUser userEntity){
        String password = userEntity.getPassword();
        password = new BCryptPasswordEncoder().encode(password);
        userEntity.setPassword(password);
    }
}
