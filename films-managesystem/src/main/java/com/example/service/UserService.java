package com.example.service;

import com.example.exception.CustomException;
import com.example.mapper.UserMapper;
import com.example.pojo.User;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Resource
    private UserMapper userMapper;

    /**
     * 登录
     */
    public User login(User user) {
        User dbuser = userMapper.selectByUsername(user.getUsername());
        if (null == dbuser) {
            //没有用户
            throw new CustomException("未查询到该账户");

        }//比较密码
        if (!user.getPassword().equals(dbuser.getPassword())) {
            throw new CustomException("账号或密码错误");
        }

        return dbuser;
    }
}
