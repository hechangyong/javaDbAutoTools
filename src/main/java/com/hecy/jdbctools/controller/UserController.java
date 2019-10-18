package com.hecy.jdbctools.controller;

import com.hecy.jdbctools.pojo.User;
import com.hecy.jdbctools.server.UserServce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
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

    @GetMapping("/getUser")
    public User getUser() {
        List<User> user = userServce.selectUser();
        return user.get(0);
    }



}
