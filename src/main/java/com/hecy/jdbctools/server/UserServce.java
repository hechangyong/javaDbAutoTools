package com.hecy.jdbctools.server;

import com.hecy.jdbctools.dao.UserDao;
import com.hecy.jdbctools.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: hecy
 * @Date: 2019/10/18 17:18
 * @Version 1.0
 */
@Component
public class UserServce {

    @Autowired
    UserDao userDao;

    public List<User> selectUser(){
        return userDao.selectUser();
    }
}
