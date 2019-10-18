package com.hecy.jdbctools.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Author: hecy
 * @Date: 2019/10/18 16:40
 * @Version 1.0
 */
public class XmlReadTools {
    private static Logger logger = LoggerFactory.getLogger(XmlReadTools.class);
    private static ApplicationContext cxt = null;

    /**
     * 初始化ctx对象
     *
     * @param xmlFileName xml文件名：dbConfig.xml
     */
    public XmlReadTools(String xmlFileName) {
        cxt = new ClassPathXmlApplicationContext(xmlFileName);
    }

    /**
     * 获取配置文件的数据
     *
     * @param beanId
     * @return
     */
    public static Object getSource(String beanId) {
        logger.info("获取bean实例：" + beanId);
        Object source = cxt.getBean(beanId);
        return source;
    }

}
