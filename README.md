# javaDbAutoTools
#### XML 数据库描述文件样例
```
<?xml version="1.0" encoding="UTF-8"?>
<dataDictionary>
    <db dbName="javaDbAutoTools" dbZHName=数据库中文名" packageName="com.hecy.jdbctools">
        <table id="user" tableZHName="用户表" >
            <field name="id" isPrimaryKey="true" nullAble="false" type="bigint" typeTag="number" precision="(20)"
                   fieldZHName="主键ID" description=""/>
            <field name="nickname" type="varchar" precision="(20)" fieldZHName="别名" description="别名"/>
            <field name="name" type="varchar" precision="(20)" fieldZHName="姓名" description="姓名"/>
            <field name="phone" isIndexes="true" type="varchar" precision="(20)" fieldZHName="电话号码" description=""/>
            <field name="address" type="varchar" precision="(20)" fieldZHName="住址" description=""/>
            <field name="itime" type="date" precision="[yyyy-MM-dd HH:ss]" fieldZHName="" description="入库时间"
                   isAutoUpdate="false"/>
            <field name="utime" type="date" precision="[yyyy-MM-dd HH:ss]" fieldZHName="" description="更新时间"/>
        </table>
    </db>
</dataDictionary>
```
##### xml 字段解释
`<db>` 标签描述所在数据库相关信息
- `packageName`: 生成代码所在包根路径。

`<table>` 标签表示表相关信息
- `id`: 表名，生成的POJO类（首字母大写，如果有下划线，会变成驼峰命名。）
- `tableZHName`:表中文解释

`<field>` 字段相关信息，相关属性说明如下：
- `name`： 字段名
-  `type`: 数据库类型,数据库类型相关枚举如下：
```
 <#macro  typeHandler jdbctype>
    <#switch  jdbctype>
        <#case 'varchar'> String<#break>
        <#case 'mediumtext'>String<#break>
        <#case 'longtext'>String<#break>
        <#case 'char'> String<#break>
        <#case 'bigint'> Long<#break>
        <#case 'date'> Date <#break>
        <#case 'datetime'> Date<#break>
        <#case 'int'> Integer<#break>
        <#case 'bit'> Boolean<#break>
        <#case 'BIT'> Boolean<#break>
        <#case 'bigdecimal'> BigDecimal<#break>
    </#switch>
</#macro>
```
- `precision`: 字段精度。 如果数据类型是`varchar`可以写`(100)`表示字符长度100。如果是`bigdecimal`可写`(10,2)`
- `isIndexes`： 表示字段此字段是否是索引字段`true,false`
- `nullAble`: 数据库字段是否可以为空  
- `isAutoUpdate`: 是否自动跟新



#### 自动生成的代码使用说明
- POM文件中需要自主添加以下依赖
 ```
    <dependency>
        <groupId>com.alibaba</groupId>
        <artifactId>druid</artifactId>
        <version>1.1.10</version>
    </dependency>
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
    </dependency>
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <version>1.18.2</version>
        <scope>provided</scope>
    </dependency>
 ```
- 项目中的application.yml 中需要配置如下数据库配置
```
spring:
  datasource:
    driverClassName: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://******:3306/dbtools?serverTimezone=GMT%2B8
    username: dbadmin
    password: dbadmin


```

