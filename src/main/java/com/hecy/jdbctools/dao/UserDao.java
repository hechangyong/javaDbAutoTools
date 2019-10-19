package com.hecy.jdbctools.dao;

import com.hecy.jdbctools.dao.basedao.AbstractUserBaseDao;
import com.hecy.jdbctools.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserDao extends AbstractUserBaseDao {


    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<User> selectUser1(){
        String sql = " select id,user_name,password ,age from user";
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper(User.class));
    }

}
