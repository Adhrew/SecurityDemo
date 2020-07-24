package com.myp.allknow.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.myp.allknow.entity.User;
import com.myp.allknow.mapper.UserMapper;
import com.myp.allknow.pojo.LoginUser;
import com.myp.allknow.pojo.SimpleUser;
import com.myp.allknow.pojo.UserCondition;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@EnableCaching
public class UserService extends ServiceImpl<UserMapper, User> {
    private Page<User> page = new Page<>();
    
    public int registerUser(SimpleUser user) {
        if(selectOne(user.getUserName())!=null)
            return -2;
        return baseMapper.registerUser(user);
    }

    public Page<User> selectPageVo(Integer current, Integer size){
        page.setCurrent(current);
        page.setSize(size);
        List<User> users = baseMapper.selectPageVo(page);
        page.setRecords(users);
        return page;
    }

    public Page<User> selectPageByCondition(Integer current, Integer size, UserCondition condition){
        page.setCurrent(current);
        page.setSize(size);
        List<User> users = baseMapper.selectPageByCondition(page,condition);
        page.setRecords(users);
        return page;
    }

    public Page<User> selectPageByProvider(Integer current, Integer size,UserCondition condition){
        page.setCurrent(current);
        page.setSize(size);
        List<User> users = baseMapper.selectPageByProvider(page,condition);
        page.setRecords(users);
        return page;
    }

    public LoginUser findByUsername(String username){
        return baseMapper.findByUsername(username);
    }

    /*
    * 使用baseMapper接口方法selectOne
    * */
    public User selectOne(String userName) {
        User user = new User();
        user.setUserName(userName);
        return baseMapper.selectOne(user);
    }

    /*
     * 使用baseMapper接口方法selectCount
     * */
    public int selectCount(EntityWrapper<User> wrapper) {
        return baseMapper.selectCount(wrapper);
    }
}
