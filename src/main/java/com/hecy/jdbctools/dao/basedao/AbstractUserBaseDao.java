package com.hecy.jdbctools.dao.basedao;

import com.hecy.jdbctools.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;
import java.util.List;

/**
 * @Author: hecy
 * @Date: 2019/10/18 17:11
 * @Version 1.0
 */
public abstract class AbstractUserBaseDao implements IBaseDao<User> {

    @Autowired
    JdbcTemplate jdbcTemplate;


    public List<User> selectUser(){
       return null;
    }

    @Override
    public void insert(User user) {
        String sql = "insert into user(user_name,password,age)values(?,?,?)";
        Object args[] = {user.getUser_name(),user.getPassword(),user.getAge()};
        int temp = jdbcTemplate.update(sql, args);
        if (temp > 0) {
            System.out.println("插入成功！");
        }else{
            System.out.println("插入失败");
        }
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public List<User> select() {

        String sql = " select id,user_name,password ,age from user";
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper(User.class));
    }


}
