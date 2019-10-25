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

<#macro  nullAble nullAbleValue>
    <#switch  nullAbleValue>
        <#case 'true'>  NULL <#break>
        <#case 'false'> NOT NULL <#break>
        <#default > NULL
    </#switch>
</#macro>

<#macro  ckeckIndex filedList>
    <#list filedList as field>
        <#if  field.isIndexes?? && field.isIndexes="true">
            <#global "haseIndexe"="true"/>
        </#if>
    </#list>
</#macro>


<#macro  defaultValue jdbctype>
    <#switch  jdbctype>
        <#case 'date'> DEFAULT CURRENT_TIMESTAMP <#break>
        <#case 'DATETIME'> DEFAULT CURRENT_TIMESTAMP <#break>
        <#case 'int'> DEFAULT '0' <#break>
        <#case 'bit'> DEFAULT b'0' <#break>
    </#switch>
</#macro>

<#list tableModels as tableModel>
    CREATE TABLE `${tableModel.id}` (
      `id` bigint(20) NOT NULL  AUTO_INCREMENT COMMENT '主键',
    <#list tableModel.subList as field>
        <#if field.name !="id" || field.name !="itime"||field.name !="utime"||field.name !="isdelete">
            <#if field.type == "date" || field.type == "datetime" || field.type ="timestamp">
                <#if field.isAutoUpdate?? && field.isAutoUpdate ="true">
	  `${field.name}` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '${field.fieldZHName}',
                <#else >
      `${field.name}` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '${field.fieldZHName}',
                </#if>
            <#else>
      `${field.name}` ${field.type}${field.precision} <@defaultValue jdbctype="${field.type}"/>  <#if field.nullAble??><@nullAble nullAbleValue="${field.nullAble}"/> <#else >NULL </#if>  COMMENT '${field.fieldZHName}',
            </#if>
        </#if>
    </#list>
    `itime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `utime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `isdelete` bit(1) DEFAULT b'0',
    <#list tableModel.subList as field>
        <#if  field.isIndexes?? && field.isIndexes="true">
     KEY `index_${field?index}` (`${field.name}`),
        </#if>
    </#list>
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB   DEFAULT CHARSET=utf8;


</#list>
