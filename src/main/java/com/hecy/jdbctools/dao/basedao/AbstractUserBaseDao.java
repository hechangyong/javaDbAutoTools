package com.hecy.jdbctools.dao.basedao;

import com.hecy.jdbctools.pojo.User;
import com.hecy.jdbctools.pojo.basePojo.PageList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;

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
            log.info("插入成功！");
            return temp;
        } else {
            System.out.println("插入失败");
            return 0;
        }
    }

    @Override
    public int deleteById(Long id) {
        String deleteSql = "delete  from user where id =  ?";
        String logicDeleteSql = "update user set isdelete = 1 where id = ?";
        int temp = jdbcTemplate.update(deleteSql, id);
        if (temp > 0) {
            System.out.println("删除s成功！");
            return temp;
        } else {
            System.out.println(" 删除失败 ");
            return -1;
        }

    }

    /**
     * 根据查询条件批量删除
     * 如果配置文件配置了逻辑删除，则启用逻辑删除
     * 否则直接物理删除
     *
     * @param condition 删除条件
     * @return
     * @throws Exception
     */
    @Override
    public int deleteByCondtion(String condition) throws Exception {
        //todo
        return 0;
    }


    @Override
    public int updateById(Map<String, Object> param, Long id) {
        StringBuffer sb = new StringBuffer();
        if (param == null || param.isEmpty()) {
            log.error("updateById param is null");
            return -1;
        }
        sb.append("set ");
        param.forEach((key, value) -> {
            sb.append(key + " = '" + value + "' , ");
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

    /**
     * 根据条件查询结果
     *
     * @param condition 没有占位符的条件字符串例如： id=1 and name = '李三'
     * @return 返回查询结果
     */
    @Override
    public List<User> selectByCondition(String condition) {
        String sql = " select id,user_name,password ,age from user";
        if (condition != null && !condition.isEmpty()) {
            sql = sql + " where " + condition;
        }
        List<User> users = jdbcTemplate.query(sql, new BeanPropertyRowMapper(User.class));
        if (users.size() > 0) {
            return users;
        }
        return null;
    }

    /**
     * 只查询一列数据类型对象。用于只有一行查询结果的数据
     *
     * @param sql    帶有佔位符的sql語句
     * @param params 匹配上面的占位符参数
     * @param cla    Integer.class,Float.class,Double.Class,Long.class,Boolean.class,Char.class,Byte.class,Short.class
     * @return 返回查询结果
     */
    @Override
    public Object selectOneColumn(String sql, Object[] params, Class cla) {
        Object result = null;
        try {
            if (params == null || params.length > 0) {
                result = jdbcTemplate.queryForObject(sql, params, cla);
            } else {
                result = jdbcTemplate.queryForObject(sql, cla);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    /**
     * @param sql
     * @param params
     * @return
     */
    @Override
    public List<User> seletctAllByParam(String sql, Object[] params) {
        List<User> users = null;
        try {
            if (params == null || params.length > 0) {
                users = jdbcTemplate.query(sql, params, new BeanPropertyRowMapper(User.class));
            } else {
                users = jdbcTemplate.query(sql, new BeanPropertyRowMapper(User.class));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return users;
    }


    /**
     * 查询分页（MySQL数据库）
     *
     * @param conditionWithPlaceholder 带有占位符? 的 查询条件语句
     * @param params                   填充sql语句中的问号占位符数
     * @param page                     想要第几页的数据
     * @param pagerow                  每页显示多少条数
     * @return pageList对象
     */
    @Override
    public PageList queryByPage(String conditionWithPlaceholder, Object[] params, int page, int pagerow) throws Exception {
        //查询总行数sql
        String rowsql = "select count(*) from user ";
        String querySql = "select * from user ";
        if (conditionWithPlaceholder != null && !conditionWithPlaceholder.isEmpty()) {
            if (params == null || counter(conditionWithPlaceholder, '?') != params.length) {
                throw new Exception("条件中占位符与参数需匹配！");
            }
            rowsql = rowsql + " where " + conditionWithPlaceholder;
            querySql = querySql + " where " + conditionWithPlaceholder;
        }
        if (conditionWithPlaceholder == null || conditionWithPlaceholder.isEmpty()) {
            if (params != null && params.length > 0) {
                params = null;
            }
        }

        //总页数
        int pages = 0;
        //查询总行数
        int rows = (Integer) selectOneColumn(rowsql, params, Integer.class);
        //判断页数,如果是页大小的整数倍就为rows/pageRow如果不是整数倍就为rows/pageRow+1
        if (rows % pagerow == 0) {
            pages = rows / pagerow;
        } else {
            pages = rows / pagerow + 1;
        }
        //查询第page页的数据sql语句
        if (page <= 1) {
            querySql += " limit 0," + pagerow;
        } else {
            querySql += " limit " + ((page - 1) * pagerow) + "," + pagerow;
        }
        //查询第page页数据
        List<User> list = seletctAllByParam(querySql, params);

        return new PageList(page, pages, rows, list);
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

    /**
     * 根据多id删除
     *
     * @param ids
     * @return
     * @throws Exception
     */
    @Override
    public int deleteByIds(List<Long> ids) throws Exception {
        //todo
        //批量校验policys
        if (ids == null || ids.stream().anyMatch(id -> id == null || id < 1L)) {
            throw new Exception("请输出有效id");
        }
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
        String sql = "select * from user ";
        List<User> users = null;
        try {
            users = jdbcTemplate.query(sql, new BeanPropertyRowMapper(User.class));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return users;
    }


    //6、数量条数查询
    public int selectCount(String condition) {

        return 0;
    }

    //8、根据唯一索引proposalNumber查询
    public User selectByProposalNumber(String proposalNumber) {
        //todo
        return null;
    }

    /**
     * 计算一个字符在字符串中出现的次数
     *
     * @param s
     * @param c
     * @return
     */
    private static int counter(String s, char c) {
        int count = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == c) {
                count++;
            }
        }
        return count;
    }
}
