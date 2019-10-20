package com.hecy.jdbctools.generate.bean;

 import com.hecy.jdbctools.generate.bean.base.BaseModel;
 import com.hecy.jdbctools.generate.bean.base.SubModel;
 import lombok.Data;

/**
 * @Author: hecy
 * @Date: 2019/7/18 16:57
 * @Version 1.0
 */
@Data
public class DbInfo extends BaseModel<TableInfo> implements SubModel {

    String dbName;
    String dbZHName;
    String packageName;
}
