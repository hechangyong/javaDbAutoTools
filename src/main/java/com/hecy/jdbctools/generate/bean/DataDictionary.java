package com.hecy.jdbctools.generate.bean;

 import com.hecy.jdbctools.generate.bean.base.BaseModel;
 import lombok.Data;

/**
 * @Author: hecy
 * @Date: 2019/7/3 17:43
 * @Version 1.0
 */
@Data
public class DataDictionary extends BaseModel<DbInfo> {


    private String dataSourceId;

    private String dataSourceZHName;


}
