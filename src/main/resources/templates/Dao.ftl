package ${packageName}.dao;


import ${packageName}.pojo.PageList;
import ${packageName}.dao.basedao.Abstract${tableModel.id?cap_first}BaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;


@Component
public class UserDao extends Abstract${tableModel.id?cap_first}BaseDao {


    @Autowired
    JdbcTemplate jdbcTemplate;


}

