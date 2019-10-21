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

<#list tableModel.subList as field>
    /**
    * ${field.fieldZHName}
    * ${field.description}
    */
    private <@typeHandler jdbctype="${field.type}"/> ${field.name};

</#list>

<#list tableModel.subList as field>
    public  <@typeHandler jdbctype="${field.type}"/> get${field.name?cap_first}(){
        return ${field.name};
    }
    <#if  field.name != "id">
    public void set${field.name?cap_first}(<@typeHandler jdbctype="${field.type}"/> ${field.name}){
        this.${field.name} = ${field.name};
    }
    </#if>
</#list>



}