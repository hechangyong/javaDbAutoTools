package com.hecy.jdbctools.dao.basedao;

import java.util.List;

public interface IBaseDao<T> {

    public void insert(T t);

    public void deleteById(Long id);

    public List<T> select();


}
