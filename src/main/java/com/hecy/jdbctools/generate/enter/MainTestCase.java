package com.hecy.jdbctools.generate.bean.enter;

import freemarker.ext.dom.NodeModel;
import freemarker.template.*;
import javafx.collections.ObservableMap;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * @Author: hecy
 * @Date: 2019/10/18 17:51
 * @Version 1.0
 */
public class MainTestCase {
    static Configuration cfg = new Configuration(new Version("2.3.23"));





    public static void main(String[] args) throws TemplateException, ParserConfigurationException, SAXException, IOException {
        genDao();
//        genPojo();
    }

    /**
     * 第一種方案，直接讀取文件，全文件搜索
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws TemplateException
     */
    public static void genPojo() throws IOException, ParserConfigurationException, SAXException, TemplateException {
        cfg.setDirectoryForTemplateLoading(new File("E:\\mygitCode\\javaDbAutoTools\\src\\main\\resources\\templates"));
        Template template = cfg.getTemplate("pojo.ftl");
        Map dataModel = new HashMap<String, Object>(16);
        File xmlFile = new File("E:\\mygitCode\\javaDbAutoTools\\src\\main\\resources\\design\\dataDictionary.xml");
        NodeModel nodeModel = NodeModel.parse(xmlFile);
        nodeModel.get("dataDictionary.dataSource.db");
        dataModel.put("doc", nodeModel);
        OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream("FileName.java"));
        template.process(dataModel, out);
        out.flush();
        out.close();
    }

    public static void genDao() throws IOException, ParserConfigurationException, SAXException, TemplateException {
        cfg.setDirectoryForTemplateLoading(new File("E:\\mygitCode\\javaDbAutoTools\\src\\main\\resources\\templates"));
        Template template = cfg.getTemplate("AbstractBaseDao.ftl");
        Map dataModel = new HashMap<String, Object>(16);
        File xmlFile = new File("E:\\mygitCode\\javaDbAutoTools\\src\\main\\resources\\design\\dataDictionary.xml");
        NodeModel nodeModel = NodeModel.parse(xmlFile);
        dataModel.put("doc", nodeModel);
        OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream("AbstractBaseDao.java"));
        template.process(dataModel, out);
        out.flush();
        out.close();
    }


}
