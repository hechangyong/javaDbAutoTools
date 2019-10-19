package ${doc.dataDictionary.dataSource.db.@package}.pojo
<#assign tableModel=doc.dataDictionary.dataSource.db.table/>
<#list tableModel.field as prop>
<@importHandler jdbctype="${prop.@type}"/>

</#list>
<#macro  importHandler jdbctype>
    <#switch  jdbctype>
        <#case 'date'>import java.util.Date; <#break>
        <#case 'DATETIME'>import java.util.Date; <#break>
        <#case 'bigdecimal'>import java.math.BigDecimal;<#break>
    </#switch>
</#macro>
<#macro  typeHandler jdbctype>
    <#switch  jdbctype>
        <#case 'varchar'> String <#break>
        <#case 'MEDIUMTEXT'> String <#break>
        <#case 'char'> String <#break>
        <#case 'bigint'> Long <#break>
        <#case 'date'> Date <#break>
        <#case 'DATETIME'> Date <#break>
        <#case 'int'> Integer <#break>
        <#case 'bit'> Boolean <#break>
        <#case 'BIT'> Boolean <#break>
        <#case 'bigdecimal'> BigDecimal <#break>
        <#default> String
    </#switch>
</#macro>


public class ${tableModel.@id?cap_first} {

<#list tableModel.field as field>
    /**
    * ${field.@fieldZHName}
    * ${field.@description}
    */
    private <@typeHandler jdbctype="${field.@type}"/> ${field.@name};

</#list>

<#list tableModel.field as field>
    public  <@typeHandler jdbctype="${field.@type}"/> get${field.@name?cap_first}(){
        return ${field.@name};
    }
<#if  field.@name != "id">
    public void set${field.@name?cap_first}(<@typeHandler jdbctype="${field.@type}"/> ${field.@name}){
        this.${field.@name} = ${field.@name};
    }
</#if>
</#list>



}