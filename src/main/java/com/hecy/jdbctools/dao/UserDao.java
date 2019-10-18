package com.hecy.jdbctools.dao;

import com.hecy.jdbctools.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: hecy
 * @Date: 2019/10/18 17:11
 * @Version 1.0
 */
@Component
public class UserDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<User> selectUser(){
        String sql = " select id,user_name,password ,age from user";
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper(User.class));
    }


}
