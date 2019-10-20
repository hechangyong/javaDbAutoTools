package com.hecy.jdbctools.generate.xmlhandle;

/**
 * @Author: hecy
 * @Date: 2019/7/4 10:44
 * @Version 1.0
 */
public interface HandleXmlBase<T> {


    void generateXml();

    void loadXml();

    void updateGlobalXmlData();

}
