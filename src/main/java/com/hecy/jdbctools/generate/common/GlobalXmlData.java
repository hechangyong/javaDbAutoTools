package com.hecy.jdbctools.generate.common;


import com.hecy.jdbctools.generate.bean.DataDictionary;
import com.hecy.jdbctools.generate.bean.DataSourceInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: hecy
 * @des 全局的 xml 配置文件数据
 * @Date: 2019/7/3 17:36
 * @Version 1.0
 */
public class GlobalXmlData {


    /**
     * 数据源数据
     */
    public static Map<String, DataSourceInfo> msrsDatasourceMapper = new HashMap<>();


    /**
     * 数据字典全局数据
     */
    public static Map<String, DataDictionary> msrsDataDictionaryMapper = new HashMap<>();


    /**
     * 生成数据字典map KEY
     * @param dataSourceId
     * @param ddId
     * @return
     */
    public static String genDDCacheKey(String dataSourceId, String ddId) {
        return dataSourceId + "##" + ddId;
    }
}
