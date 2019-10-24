package com.hecy.jdbctools.dao;

import com.hecy.jdbctools.dao.basedao.AbstractUserBaseDao;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;


@Component
public class UserDao extends AbstractUserBaseDao {


    @Autowired
    JdbcTemplate jdbcTemplate;


}
