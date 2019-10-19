package ${doc.dataDictionary.dataSource.db.@package}.dao.basedao
<#assign tableModel=doc.dataDictionary.dataSource.db.table/>

import ${doc.dataDictionary.dataSource.db.@package}.pojo.${tableModel.@id?cap_first};
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import java.util.List;

public abstract class Abstract${tableModel.@id?cap_first}BaseDao implements IBaseDao<${tableModel.@id?cap_first}> {

    @Override
    public void insert( ${tableModel.@id?cap_first} ${tableModel.@id}) {
        String sql = "insert into ${tableModel.@id}(<#list tableModel.field as field><#if field_index gt 0>, </#if> ${field.@name} </#list>)values(?,?,?)";
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
    public List<${tableModel.@id?cap_first} > select() {

    }

}