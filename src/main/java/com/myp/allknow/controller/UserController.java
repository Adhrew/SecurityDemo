package com.myp.allknow.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.myp.allknow.entity.User;
import com.myp.allknow.pojo.SimpleUser;
import com.myp.allknow.pojo.UserCondition;
import com.myp.allknow.service.UserService;
import com.myp.allknow.utils.BCryptUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "用户相关模块")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "注册用户", notes = "注册一个简单的用户")
    @PostMapping("/register")
    public String registerUser(@Valid @RequestBody SimpleUser user){
        int result;
        try{
            BCryptUtils.encryptPassword(user);
            result = userService.registerUser(user);
        }catch (Exception ex){
            result = -1;
        }
        if(result == 1) return "success";
        else if(result == -2)
            return "username is repeated!";
        else
            return "failure";
    }

    @ApiOperation(value = "获取UserName的用户")
    @GetMapping("/OneUser")
    public User OneUser(String userName){
        return userService.selectOne(userName);
    }

    @ApiOperation(value = "获取用户总数")
    @GetMapping("/UserCount")
    public int UserCount(){ return userService.selectCount(new EntityWrapper<>()); }

    @ApiOperation(value = "获取某页的用户清单")
    @GetMapping("/PageUsers")
    public List<User> PageUsers(Integer current, Integer size){
        return userService.selectPageVo(current, size).getRecords();
    }

    @ApiOperation(value = "获取指定条件的用户清单")
    @GetMapping("/PageUsersByCondition")
    public List<User> PageUsersByCondition(Integer current, Integer size,@RequestBody UserCondition condition){
        List<User> users = null;
        try {
            users = userService.selectPageByCondition(current, size,condition).getRecords();
        }catch (Exception ex){}
        return users;
    }

    @ApiOperation(value = "获取指定条件的用户清单(Provider)")
    @GetMapping("/PageUsersByProvider")
    public List<User> PageUsersByProvider(Integer current, Integer size,@RequestBody UserCondition condition){
        List<User> users = null;
        try {
            users = userService.selectPageByProvider(current, size,condition).getRecords();
        }catch (Exception ex){
            throw ex;
        }
        return users;
    }
}
