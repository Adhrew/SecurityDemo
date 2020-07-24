package com.myp.allknow.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.myp.allknow.entity.User;
import com.myp.allknow.mapper.provider.UserProvider;
import com.myp.allknow.pojo.LoginUser;
import com.myp.allknow.pojo.SimpleUser;
import com.myp.allknow.pojo.UserCondition;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface UserMapper extends BaseMapper<User> {

    /*
    * 注册用户
    * */
    @Insert("insert into user(user_name,password,sex,nickname,registration_source) " +
            "values(#{user.userName},#{user.password},#{user.sex},#{user.nickname},#{user.registrationSource})")
    public int registerUser(@Param("user") SimpleUser user);

    /*
    * mybatis-plus的分页写法
    * */
    @Select("select * from user")
    public List<User> selectPageVo(Page<User> page);


    /*
    * 注解方法的动态写法
    * */
    @Select("<script>" +
            "select * from user " +
            "where 1=1 " +
            "<when test='condition.userName!=null and condition.userName!=\"\"'>" +
            "AND instr(user_name,#{condition.userName})>0" +
            "</when>" +
            "<when test='condition.sex==1 or condition.sex==2'>" +
            "AND sex = #{condition.sex}" +
            "</when>" +
            "<when test='condition.nickname!=null and condition.nickname!=\"\"'>" +
            "AND instr(nickname,#{condition.nickname})>0" +
            "</when>" +
            "<when test='condition.birthday!=null and condition.birthday!=\"\"'>" +
            "AND instr(birthday,#{condition.birthday})>0" +
            "</when>" +
            "<when test='condition.regTime!=null'>" +
            "AND reg_time &lt; #{condition.regTime}" +
            "</when>" +
            "<when test='condition.lastLoginTime!=null and condition.lastLoginTime!=\"\"'>" +
            "AND instr(last_login_time,#{condition.lastLoginTime})>0" +
            "</when>" +
            "<when test='condition.status &lt; 5 and condition.status &gt; 0'>" +
            "AND status=#{condition.status}" +
            "</when>" +
            "<when test='condition.registrationSource &lt; 5 and condition.registrationSource &gt; 0'>" +
            "AND registration_source=#{condition.registrationSource}" +
            "</when>" +
            "</script>")
    public List<User> selectPageByCondition(Page<User> page,@Param("condition") UserCondition condition);

    /*
     * 注解方法的动态写法2 使用provider实现sql拼接
     * */
    @SelectProvider(type = UserProvider.class,method = "queryUserByCondition")
    public List<User> selectPageByProvider(Page<User> page,@Param("condition") UserCondition condition);

    @Select("select user_id,user_name,password,status from user where user_name=#{username}")
    @Results({
            @Result(id = true, property = "userId", column = "user_id"),
            @Result(property = "Roles", column = "user_id", javaType = List.class,
                    many = @Many(select = "com.myp.allknow.mapper.RoleMapper.findByUid"))
    })
    public LoginUser findByUsername(String username);
}
