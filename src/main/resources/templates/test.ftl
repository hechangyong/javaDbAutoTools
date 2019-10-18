
public class a {

<#list doc.dataDictionary.dataSource.db.table.field as prop>
    private ${prop.@type} ${prop.@name};
</#list>



}