package com.hecy.jdbctools.generate.bean.base;

import lombok.Data;

import java.util.List;

/**
 * @Author: hecy
 * @Date: 2019/7/19 11:17
 * @Version 1.0
 */
@Data
public abstract class BaseModel<T extends SubModel>  {
    List<T> subList;
}
