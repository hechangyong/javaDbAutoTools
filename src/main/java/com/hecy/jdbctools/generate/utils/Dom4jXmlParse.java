package com.hecy.jdbctools.generate.utils;

import com.hecy.jdbctools.config.XmlReadTools;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: hecy
 * @Date: 2019/7/3 14:35
 * @Version 1.0
 */
public class Dom4jXmlParse {
    private static Logger log = LoggerFactory.getLogger(Dom4jXmlParse.class);

    public static final Object loadKey = new Object();

    public static Document parse(File file) throws DocumentException {
        SAXReader reader = new SAXReader();
        Document document = reader.read(file);
        return document;
    }
    public static Document parse(InputStream in) throws DocumentException {
        SAXReader reader = new SAXReader();
        Document document = reader.read(in);
        return document;
    }

    public static <T> T loadMetadataElementToBean(Element eles, Class<T> tClass) {
        if (eles == null) {
            return null;
        }
        T bean = null;
        synchronized (loadKey) {
            try {
                bean = tClass.newInstance();
            } catch (Exception e) {
                log.error("序列化对象出现异常：{}", e.getMessage(), e);
            }
            List<Attribute> attrs = eles.attributes();
            Map<String, String> attrsMap = new HashMap<>();
            for (Attribute attr : attrs) {
                attrsMap.put(attr.getName(), attr.getValue());
            }
            MapUtil.mapToBean(attrsMap, bean);
        }
        return bean;
    }


    public static void XMLWriter(Document document, File dest) {

        OutputFormat format = OutputFormat.createPrettyPrint();
        ///设置输出文件的编码
        format.setEncoding("UTF-8");
        try {
            // 创建XMLWriter对象
            @Cleanup XMLWriter writer = new XMLWriter(new FileOutputStream(dest), format);
            //设置不自动进行转义
            writer.setEscapeText(false);
            // 生成XML文件
            writer.write(document);

        } catch (IOException e) {
            log.error("生成文件出现异常{}", e.getMessage(), e);
        }
    }

}
