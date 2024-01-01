package com.example.mapper;

import com.example.pojo.User;
import org.apache.ibatis.annotations.Select;

public interface UserMapper {
    @Select("select * from user where username = #{username}")
    User selectByUsername(String username);
}
