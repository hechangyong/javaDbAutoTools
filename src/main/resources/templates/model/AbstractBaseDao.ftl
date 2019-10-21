package ${doc.dataDictionary.dataSource.db.@package}.dao.basedao
<#assign tableModel=doc.dataDictionary.dataSource.db.table/>

import ${doc.dataDictionary.dataSource.db.@package}.pojo.${tableModel.@id?cap_first};
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import java.util.List;

public abstract class Abstract${tableModel.@id?cap_first}BaseDao implements IBaseDao<${tableModel.@id?cap_first}> {

    @Autowired
    JdbcTemplate jdbcTemplate;


    @Override
    public void insert( ${tableModel.@id?cap_first} ${tableModel.@id}) {
        String sql = "insert into ${tableModel.@id}(<#list tableModel.field as field><#if field_index gt 0>, </#if> ${field.@name} </#list>) values(<#list tableModel.field as field><#if field_index gt 0>, </#if>?</#list>)";
        Object args[] = { <#list tableModel.field as field><#if field_index gt 0>, </#if>  ${tableModel.@id}.get${field.@name?cap_first}() </#list>};
        int temp = jdbcTemplate.update(sql, args);
        if (temp > 0) {
            System.out.println("插入成功！");
        }else{
            System.out.println("插入失败");
        }
    }


    @Override
    public void deleteById(Long id) {
    <#if tableModel.@hasLogicDelete[0]??>
        <#if tableModel.@hasLogicDelete[0]?? && tableModel.@hasLogicDelete == "false"  >
         String deleteSql =   "delete  from ${tableModel.@id} where id =  ?";
        <#else>
         String deleteSql = "update ${tableModel.@id} set isdelete = 1 where id = ?";
        </#if>
    <#else>
        String deleteSql = "update ${tableModel.@id} set isdelete = 1 where id = ?";
    </#if>
        int temp = jdbcTemplate.update(deleteSql, id);
        if (temp > 0) {
            System.out.println("删除成功！");
        } else {
            System.out.println("删除失败");
        }
    }

    @Override
    public List<${tableModel.@id?cap_first} > select() {

    }

}