package com.hecy.jdbctools.generate.xmlhandle;

/**
 * @Author: hecy
 * @Date: 2019/7/4 10:44
 * @Version 1.0
 */
public interface HandleXmlBase<T> {


    void generateXml();

    /**
     * 加载XML文件
     */
    void loadXml();

    /**
     * 更新全局XML数据
     */
    void updateGlobalXmlData();

}
