# javaDbAutoTools
#### XML 数据库描述文件样例
```
<?xml version="1.0" encoding="UTF-8"?>
<dataDictionary>
    <db dbName="diudiu" dbZHName=数据库中文名" packageName="com.hecy.jdbctools">
        <table id="user" tableZHName="用户表" hasLogicDelete="false">
            <field name="id" isPrimaryKey="true" nullAble="false" type="bigint" typeTag="number" precision="(20)"
                   fieldZHName="主键ID" description=""/>
            <field name="nickname" type="varchar" precision="(20)" fieldZHName="别名" description="别名"/>
            <field name="name" type="varchar" precision="[20]" fieldZHName="姓名" description="姓名"/>
            <field name="phone" isIndexes="true" type="varchar" precision="[20]" fieldZHName="电话号码" description=""/>
            <field name="address" type="varchar" precision="[100]" fieldZHName="住址" description=""/>
            <field name="status" type="varchar" precision="[10]" fieldZHName="认证状态:1-已认证，2-为认证" description=""/>
            <field name="itime" type="date" precision="[yyyy-MM-dd HH:ss]" fieldZHName="" description="入库时间"
                   isAutoUpdate="false"/>
            <field name="utime" type="date" precision="[yyyy-MM-dd HH:ss]" fieldZHName="" description="更新时间"/>
        </table>
    </db>
</dataDictionary>
```
