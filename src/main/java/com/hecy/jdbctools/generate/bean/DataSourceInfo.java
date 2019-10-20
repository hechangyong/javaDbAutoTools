package com.hecy.jdbctools.generate.bean;

import lombok.Data;


@Data
public class DataSourceInfo {
    //数据库连接属性
    private String id;
    private String name;
    private String url;
    private String driver;
    private String username;
    private String password;
}
