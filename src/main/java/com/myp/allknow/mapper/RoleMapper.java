package com.myp.allknow.mapper;

import com.myp.allknow.pojo.UserRole;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RoleMapper{
    @Select("select r.id,r.role_name,r.user_role " +
            "FROM userRole r,user ur " +
            "WHERE instr(ur.role_id,r.id)>0 AND ur.user_id=#{uid}")
    public List<UserRole> findByUid(Integer uid);
}
