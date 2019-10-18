package com.hecy.jdbctools.generate.bean;

 import com.hecy.jdbctools.generate.bean.base.SubModel;
 import lombok.Data;

/**
 * @Author: hecy
 * @Date: 2019/7/2 15:44
 * @Version 1.0
 */
@Data
public class FieldInfo implements SubModel {
    /**
     * 字段名称
     * 例如: sex
     */
    private String name;

    /**
     * 数据库字段类型
     * 例：varchar
     */
    private String type;

    /**
     * 精度
     * 时间类型：[yyyy-MM-dd] 表示精确到年月日
     * bigdecimal：[10,2] 表示保留2位小数
     * long: [100] 100位Long 数据长度
     */
    private String precision;

    /**
     * 中文名称
     * 前端展示使用
     */
    private String fieldZHName;

    /**
     * 描述
     */
    private String description;

    /**
     * 字段值枚举值
     * 例如："0-男|1-女"
     */
    private String valueOption;

    /**
     * 字段映射关系
     * 例如：tableid.field 此字段和表tableid 中字段field  关联
     */
    private String refer;


    /**
     * 字段类型标签
     * 例如：数值型，字符型，时间型
     */
    private String typeTag;



}
