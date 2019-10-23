package com.hecy.jdbctools.dao.basedao;

import java.util.List;
import java.util.Map;

public interface IBaseDao<T> {

    public int insert(T t);

    public void deleteById(Long id);

    public int updateById(Map<String,Object>  param, Long id);

    public T selectById(Long id);

    public List<T> selectByCondition(String sql, String condition);
    public List<T> selectAll();





}
