package com.hecy.jdbctools.server;

import com.hecy.jdbctools.dao.UserDao;
import com.hecy.jdbctools.pojo.User;
import com.hecy.jdbctools.pojo.basePojo.PageList;
import org.hibernate.validator.constraints.LuhnCheck;
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

    public List<User> selectUser() {
        return userDao.selectUser();
    }


    public List<User> selectUser1() {
        return userDao.selectUser();
    }

    public User selectById() {
        return userDao.selectById(2L);
    }

    public PageList<User> queryByPage() {
//        String conditionWithPlaceholder, Object[] params, int page, int pagerow
        try {
            return userDao.queryByPage("", new Object[]{2}, 0, 10);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void insert(User user) {
        userDao.insert(user);
    }

    public void deleteById(User user) {
        userDao.deleteById(1L);
    }
}
