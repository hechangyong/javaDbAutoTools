package com.hecy.jdbctools.generate.config;

import com.hecy.jdbctools.generate.xmlhandle.handle.DataDictionaryXmlHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @Author: hecy
 * @Date: 2019/7/16 14:38
 * @Version 1.0
 * 启动加载XML 数据字典信息
 */
@Component
public class XmlDataInit implements CommandLineRunner {

    @Autowired
    DataDictionaryXmlHandle dataDictionaryXmlHandle;


    @Override
    public void run(String... args) {
         dataDictionaryXmlHandle.loadXml();
     }
}
