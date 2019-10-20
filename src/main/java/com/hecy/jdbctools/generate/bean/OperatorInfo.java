package com.hecy.jdbctools.generate.bean;

import lombok.Data;

import java.util.List;

/**
 * @Author: hecy
 * @Date: 2019/7/12 16:42
 * @Version 1.0
 */
@Data
public class OperatorInfo {
    private String id;
    private String name;
    private String tag;
    private List<OpFactor> opFactors;


}
