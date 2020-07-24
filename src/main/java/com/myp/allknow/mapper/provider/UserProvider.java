package com.myp.allknow.mapper.provider;

import com.myp.allknow.pojo.UserCondition;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

public class UserProvider {
    private final String TBL_ORDER = "user";

    public String queryUserByCondition(UserCondition condition) {
        SQL sql = new SQL().SELECT("*").FROM(TBL_ORDER);
        String userName = condition.getUserName();
        if (StringUtils.hasText(userName)) {
            sql.WHERE("instr(user_name,#{condition.userName})>0");
        }
        Integer sex = condition.getSex();
        if(sex != null && (sex == 1 || sex == 2))
            sql.WHERE("sex = #{condition.sex}");
        String nickname = condition.getNickname();
        if(StringUtils.hasText(nickname))
            sql.WHERE("instr(nickname,#{condition.nickname})>0");
        //后面省略
        return sql.toString();
    }
}

