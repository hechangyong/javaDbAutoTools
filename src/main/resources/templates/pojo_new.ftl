package ${packageName}.pojo

import java.math.BigDecimal;
import java.util.Date;

<#macro  typeHandler jdbctype>
    <#switch  jdbctype>
        <#case 'varchar'> String<#break>
        <#case 'mediumtext'>String<#break>
        <#case 'longtext'>String<#break>
        <#case 'char'> String<#break>
        <#case 'bigint'> Long<#break>
        <#case 'date'> Date<#break>
        <#case 'datetime'> Date<#break>
        <#case 'int'> Integer<#break>
        <#case 'bit'> Boolean<#break>
        <#case 'BIT'> Boolean<#break>
        <#case 'bigdecimal'> BigDecimal<#break>
    </#switch>
</#macro>

public class ${tableModel.id?cap_first} {

    /**
    * 主键
    */
    private Long id;

    /**
    * 逻辑删除字段
    */
    private boolean isdelete;

    /**
    * 插入时间
    */
    private Date utime;

    /**
    * 更新时间
    */
    private Date itime;
<#list tableModel.subList as field>
    <#if field.name !="id" || field.name !="itime"||field.name !="utime"||field.name !="isdelete">
    /**
    * ${field.fieldZHName}
    * ${field.description}
    */
    private <@typeHandler jdbctype="${field.type}"/> ${field.name};
    </#if>
</#list>

<#list tableModel.subList as field>
    <#if field.name !="id" || field.name !="itime"||field.name !="utime"||field.name !="isdelete">
    public  <@typeHandler jdbctype="${field.type}"/> get${field.name?cap_first}(){
        return ${field.name};
    }
    public void set${field.name?cap_first}(<@typeHandler jdbctype="${field.type}"/> ${field.name}){
        this.${field.name} = ${field.name};
    }
    </#if>
</#list>
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getUtime() {
        return utime;
    }

    public void setUtime(Date utime) {
        this.utime = utime;
    }

    public Date getItime() {
        return itime;
    }

    public void setItime(Date itime) {
        this.itime = itime;
    }

    public boolean isIsdelete() {
        return isdelete;
    }

    public void setIsdelete(boolean isdelete) {
        this.isdelete = isdelete;
    }

}