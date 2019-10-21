package com.hecy.jdbctools.generate.bean;

 import com.hecy.jdbctools.generate.bean.base.BaseModel;
 import com.hecy.jdbctools.generate.bean.base.SubModel;
 import lombok.Data;

/**
 * @Author: hecy
 * @Date: 2019/7/3 17:13
 * @Version 1.0
 */
@Data
public class TableInfo  extends BaseModel<FieldInfo> implements SubModel {

    private String id;

    private String tableZHName;

    private String referTables;

    private String hasLogicDelete;

}
