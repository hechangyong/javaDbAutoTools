package com.hecy.jdbctools.dao.basedao;

import com.hecy.jdbctools.generate.enter.TempleFileHandle;
import com.hecy.jdbctools.pojo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: hecy
 * @Date: 2019/10/18 17:11
 * @Version 1.0
 */
public abstract class AbstractUserBaseDao implements IBaseDao<User> {
    private static Logger log = LoggerFactory.getLogger(AbstractUserBaseDao.class);

    @Autowired
    JdbcTemplate jdbcTemplate;


    @Override
    public int insert(User user) {
        String sql = "insert into user(user_name,password,age)values(?,?,?)";
        Object args[] = {user.getUser_name(), user.getPassword(), user.getAge()};
        int temp = jdbcTemplate.update(sql, args);
        if (temp > 0) {
            System.out.println("插入成功！");
            return temp;
        } else {
            System.out.println("插入失败");
            return 0;
        }
    }

    @Override
    public void deleteById(Long id) {
        String deleteSql = "delete  from user where id =  ?";
        String logicDeleteSql = "update user set isdelete = 1 where id = ?";
        int temp = jdbcTemplate.update(deleteSql, id);
        if (temp > 0) {
            System.out.println("删除成功！");
        } else {
            System.out.println("删除失败");
        }

    }

    public static void main(String[] args) {
        Map param = new HashMap<String,String>();
        param.put("s","ss");
        param.put("aa","aaaa");
        param.put("aaas","vvv");
        StringBuffer sb = new StringBuffer();
        if (param == null || param.isEmpty()) {
            log.error("updateById param is null");

        }
        sb.append("set ");
        param.forEach((key, value) -> {
            sb.append(key + " = '" + value + "' , ");
        });
        String finalParamSql = sb.toString().substring(0, sb.toString().lastIndexOf(","));
        String logicDeleteSql = "update user " + finalParamSql + " where id = ?";
        log.info(logicDeleteSql);
    }
    @Override
    public   int updateById(Map<String, Object> param, Long id) {
        StringBuffer sb = new StringBuffer();
        if (param == null || param.isEmpty()) {
            log.error("updateById param is null");
            return -1;
        }
        sb.append("set ");
        param.forEach((key, value) -> {
            sb.append(key + " = " + value + " , ");
        });
        String finalParamSql = sb.toString().substring(0, sb.toString().lastIndexOf(","));
        String logicDeleteSql = "update user " + finalParamSql + " where id = ?";
        int temp = jdbcTemplate.update(logicDeleteSql, id);
        if (temp > 0) {
            System.out.println("删除成功！");
            return temp;
        } else {
            System.out.println("删除失败");
            return -1;
        }

    }

    @Override
    public List<User> selectByCondition(String sql, String condition) {
        return null;
    }

    public List<User> selectUser() {
        return null;
    }


    @Override
    public User selectById(Long id) {

        String sql = " select id,user_name,password ,age from user where id = ?";
        String deleteSql = " select id,user_name,password ,age from user where isdelete = 1 and id = ?";
        PreparedStatementSetter preparedStatementSetter = preparedStatement -> preparedStatement.setInt(1, Integer.parseInt(id + ""));
        List<User> users = jdbcTemplate.query(sql, preparedStatementSetter, new BeanPropertyRowMapper(User.class));
        if (users != null && users.size() > 0) {
            return users.get(0);
        }
        return null;
    }

    //18、根据多id删除
    public int deleteByIds(List<Long> ids) throws Exception {
        //todo
        //批量校验policys
        if (ids == null || ids.stream().anyMatch(id -> id == null || id < 1L)) {
            throw new Exception("请输出有效id");
        }
        return 0;
    }

    //19、根据查询条件批量删除
    public int deleteByCondtion(String condition) throws Exception {
        //todo
        //批量校验policys
        return 0;
    }


    // I:
    public int updateSelectiveByCondtion(String condition, User user) {
        //todo
        return 0;
    }

    //13、更新policy中包含ID
    public int updateByCondtion(Map param, User user) {
        //todo
        return 0;
    }


    //2、根据id列表查询,返回列表, 如果无结果则返回空集合
    public List<User> selectByIds(Long... ids) {
        //todo
        return null;
    }

    //3、只查一条，多条记录会抛出异常，无结果返回null
    public User selectOne(User policy) {
        //todo
        return null;
    }


    @Override
    public List<User> selectAll() {
        //todo
        return null;
    }


    // I: 自定义查询
    public List<User> selectByCondition(String condition) {
        //todo
        return null;
    }

    //6、数量条数查询
    public int selectCount(String condition) {

        return 0;
    }

//    //7、分页查询
//    public Page<User> selectByPage(String condition, Integer pageNum, Integer pageSize) {
//        //todo
//        return null;
//    }

    //8、根据唯一索引proposalNumber查询
    public User selectByProposalNumber(String proposalNumber) {
        //todo
        return null;
    }


}
