package com.hecy.jdbctools.bean.dao.basedao

import com.hecy.jdbctools.bean.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import java.util.List;

public abstract class AbstractUserBaseDao implements IBaseDao<User> {

    @Override
    public void insert( User user) {
        String sql = "insert into user( id ,  nickname ,  name ,  phone ,  phone ,  address ,  status ,  itime ,  utime )values(?,?,?)";
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
    public List<User > select() {

    }

}