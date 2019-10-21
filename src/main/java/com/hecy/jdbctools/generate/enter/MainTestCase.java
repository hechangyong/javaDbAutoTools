package com.hecy.jdbctools.generate.enter;

import com.hecy.jdbctools.generate.common.GlobalXmlData;
import com.hecy.jdbctools.generate.xmlhandle.handle.DataDictionaryXmlHandle;
import com.hecy.jdbctools.generate.xmlhandle.handle.DataSourceXmlHandle;
import freemarker.ext.dom.NodeModel;
import freemarker.template.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: hecy
 * @Date: 2019/10/18 17:51
 * @Version 1.0
 */
@Component
public class MainTestCase {
    static Configuration cfg = new Configuration(new Version("2.3.23"));

    @Autowired
    DataSourceXmlHandle dataSourceXmlHandle;


    @Autowired
    DataDictionaryXmlHandle dataDictionaryXmlHandle;


    public void mainTestCase(InputStream inputStream) {
        dataDictionaryXmlHandle.loadXmlnew(inputStream);
        try {
            genPojonew();
//            genDaoNew();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 第一種方案，直接讀取文件，全文件搜索
     *
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws TemplateException
     */
    public void genPojo() throws IOException, ParserConfigurationException, SAXException, TemplateException {


        cfg.setDirectoryForTemplateLoading(new File("E:\\mygitCode\\javaDbAutoTools\\src\\main\\resources\\templates"));
        Template template = cfg.getTemplate("pojo.ftl");
        Map dataModel = new HashMap<String, Object>(16);
        File xmlFile = new File("E:\\mygitCode\\javaDbAutoTools\\src\\main\\resources\\design\\dataDictionary.xml");
        NodeModel nodeModel = NodeModel.parse(xmlFile);
        dataModel.put("doc", nodeModel);
        OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream("FileName.java"));
        template.process(dataModel, out);
        out.flush();
        out.close();
    }

    public String toUpperCaseFirstOne(String s) {
        if (s == null) {
            return null;
        }
        if (Character.isUpperCase(s.charAt(0))) {
            return s;
        } else {
            return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
        }
    }

    /**
     * 第二種方案，直接讀取文件，全文件搜索
     *
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws TemplateException
     */
    public void genPojonew() throws IOException, ParserConfigurationException, SAXException, TemplateException {
        cfg.setDirectoryForTemplateLoading(new File("D:\\mygit\\javaDbAutoTools\\src\\main\\resources\\templates"));
        Template template = cfg.getTemplate("pojo_new.ftl");
        Map dataModel = new HashMap<String, Object>(16);
        GlobalXmlData.msrsDbInfosMapper.forEach((dbName, dbInfo) -> {
            dataModel.put("packageName", dbInfo.getPackageName());
            dbInfo.getSubList().forEach(tableInfo -> {
                dataModel.put("tableModel", tableInfo);
                OutputStreamWriter out = null;
                try {
                    out = new OutputStreamWriter(new FileOutputStream("D:\\mygit\\javaDbAutoTools\\tempDir\\" + toUpperCaseFirstOne(tableInfo.getId()) + ".java"));
                    template.process(dataModel, out);
                    out.flush();
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        });


    }

    public void genDao() throws IOException, ParserConfigurationException, SAXException, TemplateException {
        cfg.setDirectoryForTemplateLoading(new File("D:\\mygit\\javaDbAutoTools\\src\\main\\resources\\templates"));
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

    public void genDaoNew() throws IOException, ParserConfigurationException, SAXException, TemplateException {
        cfg.setDirectoryForTemplateLoading(new File("D:\\mygit\\javaDbAutoTools\\src\\main\\resources\\templates"));
        Template template = cfg.getTemplate("AbstractBaseDao_new.ftl");
        //        File xmlFile = new File("E:\\mygitCode\\javaDbAutoTools\\src\\main\\resources\\design\\dataDictionary.xml");
        Map dataModel = new HashMap<String, Object>(16);
        GlobalXmlData.msrsDbInfosMapper.forEach((dbName, dbInfo) -> {
            dataModel.put("packageName", dbInfo.getPackageName());
            dbInfo.getSubList().forEach(tableInfo -> {
                dataModel.put("tableModel", tableInfo);
                OutputStreamWriter out = null;
                try {
                    out = new OutputStreamWriter(new FileOutputStream("D:\\mygit\\javaDbAutoTools\\tempDir\\dao\\Abstract" + toUpperCaseFirstOne(tableInfo.getId()) + "BaseDao.java"));
                    template.process(dataModel, out);
                    out.flush();
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        });


    }


}
