package com.hecy.jdbctools.dao.basedao;

import com.hecy.jdbctools.pojo.basePojo.PageList;

import java.util.List;
import java.util.Map;

public interface IBaseDao<T> {

    public int insert(T t);

    public int deleteById(Long id);

    public int deleteByIds(List<Long> ids) throws Exception;

    public int deleteByCondtion(String condition) throws Exception;

    public int updateById(Map<String, Object> param, Long id);

    public T selectById(Long id);

    public List<T> selectByCondition(String condition);

    public List<T> seletctAllByParam(String sql, Object[] params);

    public List<T> selectAll();

    public Object selectOneColumn(String sql, Object[] params, Class cla);

    public PageList queryByPage(String conditionWithPlaceholder, Object[] params, int page, int pagerow) throws Exception;


}
