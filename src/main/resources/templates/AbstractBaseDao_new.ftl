package ${packageName}.dao.basedao;

import ${packageName}.pojo.${tableModel.id?cap_first};
import ${packageName}.pojo.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import java.util.List;

public abstract class Abstract${tableModel.id?cap_first}BaseDao implements IBaseDao<${tableModel.id?cap_first}> {

    private static Logger log = LoggerFactory.getLogger(Abstract${tableModel.id?cap_first}BaseDao.class);

    @Autowired
    JdbcTemplate jdbcTemplate;


    @Override
    public int insert( ${tableModel.id?cap_first} ${tableModel.id}) {
        String sql = "insert into ${tableModel.id}(<#list tableModel.subList as field><#if field_index gt 0>, </#if> ${field.name} </#list>) values(<#list tableModel.subList as field><#if field_index gt 0>, </#if>?</#list>)";
        Object args[] = { <#list tableModel.subList as field><#if field_index gt 0>, </#if>  ${tableModel.id}.get${field.name?cap_first}() </#list>};
        int temp = jdbcTemplate.update(sql, args);
        if (temp > 0) {
            log.info("插入成功！");
            return temp;
        } else {
            log.info("插入失败");
            return -1;
        }
    }



    @Override
    public int deleteById(Long id) {
        String deleteSql = "update ${tableModel.id} set isdelete = 1 where id = ?";
        int temp = jdbcTemplate.update(deleteSql, id);
        if (temp > 0) {
            log.info("删除成功！");
            return temp;
        } else {
            log.info("删除失败");
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
    public int updateById(Map
<String, Object> param, Long id) {
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
        String updateSql = "update  ${tableModel.id} " + finalParamSql + " where id = ?";
        int temp = jdbcTemplate.update(updateSql, id);
        if (temp > 0) {
            log.info("更新成功！");
            return temp;
        } else {
            log.info("更新失败");
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
    public List<${tableModel.id?cap_first}> selectByCondition(String condition) {
        String sql = " select id, <#list tableModel.subList as field>   ${field.name}, </#list> isdelete,itime,utime from ${tableModel.id} where isdelete = 0";
        if (condition != null && !condition.isEmpty()) {
            sql = sql + "  AND " + condition;
        }
        List<${tableModel.id?cap_first}> datas = jdbcTemplate.query(sql, new BeanPropertyRowMapper(${tableModel.id?cap_first}.class));
        if (datas.size() > 0) {
            return datas;
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
    public List<${tableModel.id?cap_first}> seletctAllByParam(String sql, Object[] params) {
        List<${tableModel.id?cap_first}> datas = null;
        try {
            if (params == null || params.length > 0) {
                datas = jdbcTemplate.query(sql, params, new BeanPropertyRowMapper(${tableModel.id?cap_first}.class));
            } else {
                datas = jdbcTemplate.query(sql, new BeanPropertyRowMapper(${tableModel.id?cap_first}.class));
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
<#if tableModel.hasLogicDelete??>
    <#if tableModel.hasLogicDelete?? && tableModel.hasLogicDelete == "false"  >
         String rowsql = "select count(*) from ${tableModel.id} ";
         String querySql = "select * from ${tableModel.id} ";
    <#else>
        String rowsql = "select count(*) from ${tableModel.id} where isdelete = 0 ";
        String querySql = "select * from ${tableModel.id} where isdelete = 0 ";
    </#if>
<#else>
        String rowsql = "select count(*) from ${tableModel.id} where isdelete = 0 ";
        String querySql = "select * from ${tableModel.id} where isdelete = 0 ";
</#if>
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
        List<${tableModel.id?cap_first}> list = seletctAllByParam(querySql, params);

        return new PageList(page, pages, rows, list);
    }

    @Override
    public User selectById(Long id) {
        String sql = "select id, <#list tableModel.subList as field>   ${field.name}, </#list> isdelete,itime,utime from ${tableModel.id} where  id = ? and isdelete = 0 ";
        PreparedStatementSetter preparedStatementSetter = preparedStatement -> preparedStatement.setInt(1, Integer.parseInt(id + ""));
        List<${tableModel.id?cap_first}> datas = jdbcTemplate.query(sql, preparedStatementSetter, new BeanPropertyRowMapper(User.class));
        if (datas != null && datas.size() > 0) {
            return datas.get(0);
        }
        return null;
    }





    @Override
    public List<${tableModel.id?cap_first}> selectAll() {
        String sql = " select id, <#list tableModel.subList as field>   ${field.name}, </#list> isdelete,itime,utime from ${tableModel.id}  ";
        List<${tableModel.id?cap_first}> users = null;
        try {
            users = jdbcTemplate.query(sql, new BeanPropertyRowMapper(${tableModel.id?cap_first}.class));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return users;
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