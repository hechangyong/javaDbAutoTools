package com.hecy.jdbctools.bean.dao.basedao

import com.hecy.jdbctools.bean.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import java.util.List;

public abstract class AbstractUserBaseDao implements IBaseDao<User> {

    @Autowired
    JdbcTemplate jdbcTemplate;


    @Override
    public void insert( User user) {
        String sql = "insert into user( id ,  nickname ,  name ,  phone ,  phone ,  address ,  status ,  itime ,  utime ) values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Object args[] = {   user.getId() ,   user.getNickname() ,   user.getName() ,   user.getPhone() ,   user.getPhone() ,   user.getAddress() ,   user.getStatus() ,   user.getItime() ,   user.getUtime() };
        int temp = jdbcTemplate.update(sql, args);
        if (temp > 0) {
            System.out.println("插入成功！");
        }else{
            System.out.println("插入失败");
        }
    }



    @Override
    public void deleteById(Long id) {
         String deleteSql =   "delete  from user where id =  ?";
        int temp = jdbcTemplate.update(deleteSql, id);
        if (temp > 0) {
            System.out.println("删除成功！");
        } else {
            System.out.println("删除失败");
        }
    }

    @Override
    public List<User > select() {

    }

}