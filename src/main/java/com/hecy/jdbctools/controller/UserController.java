package com.hecy.jdbctools.controller;

import com.hecy.jdbctools.generate.enter.MainTestCase;
import com.hecy.jdbctools.pojo.User;
import com.hecy.jdbctools.server.UserServce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

 import java.util.List;

/**
 * @Author: hecy
 * @Date: 2019/10/18 17:21
 * @Version 1.0
 */
@RestController
@RequestMapping("/a")
public class UserController {

    @Autowired
    UserServce userServce;

    @Autowired
    MainTestCase mainTestCase;
    @GetMapping("/getUser")
    public User getUser() {
        List<User> user = userServce.selectUser();
        System.out.printf("user: " + user);
        List<User> user1 = userServce.selectUser1();
        User user2 = user1.get(0);
        user2.setUser_name("new");
        userServce.insert(user2);
        userServce.deleteById(user2);
        return user1.get(0);
    }
    @GetMapping("/b")
    public void b() {
        mainTestCase.mainTestCase1();
    }


}
