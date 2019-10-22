package com.hecy.jdbctools.generate.enter;

import com.hecy.jdbctools.generate.common.GlobalXmlData;
import com.hecy.jdbctools.generate.utils.StringUtils;
import com.hecy.jdbctools.generate.xmlhandle.handle.DataDictionaryXmlHandle;
import freemarker.ext.dom.NodeModel;
import freemarker.template.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static Logger log = LoggerFactory.getLogger(MainTestCase.class);

    @Autowired
    DataDictionaryXmlHandle dataDictionaryXmlHandle;

    public void mainTestCase1() {
        dataDictionaryXmlHandle.loadXml();
        try {
            genPojonew();
//            genDaoNew();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void generateJavaFile(InputStream inputStream) {
        dataDictionaryXmlHandle.loadXmlnew(inputStream);
        try {
            genPojonew();
            genDaoNew();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            GlobalXmlData.msrsDbInfosMapper.clear();
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
    public void genPojonew() throws IOException {
        log.info("开始生成pojo java文件");
        Template template = getTemplate("pojo_new.ftl");
        Map dataModel = new HashMap<String, Object>(16);
        GlobalXmlData.msrsDbInfosMapper.forEach((dbName, dbInfo) -> {
            dataModel.put("packageName", dbInfo.getPackageName());
            dbInfo.getSubList().forEach(tableInfo -> {
                dataModel.put("tableModel", tableInfo);
                OutputStreamWriter out = null;
                try {
                    out = new OutputStreamWriter(new FileOutputStream("D:\\mygit\\javaDbAutoTools\\tempDir\\" + StringUtils.toUpperCaseFirstOne(tableInfo.getId()) + ".java"));
                    template.process(dataModel, out);
                    out.flush();
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        });


    }

    private Template getTemplate(String ftlfilename) {
        try {
            cfg.setDirectoryForTemplateLoading(new File("D:\\mygit\\javaDbAutoTools\\src\\main\\resources\\templates"));
            return cfg.getTemplate(ftlfilename);
        } catch (IOException e) {
            log.error("获取TEMPLATE文件出现异常。" + e.getMessage(), e);
            return null;
        }

    }


    public void genDaoNew() throws IOException {
        log.info("开始生成dao java文件");
        Template template = getTemplate("AbstractBaseDao_new.ftl");
        //        File xmlFile = new File("E:\\mygitCode\\javaDbAutoTools\\src\\main\\resources\\design\\dataDictionary.xml");
        Map dataModel = new HashMap<String, Object>(16);
        GlobalXmlData.msrsDbInfosMapper.forEach((dbName, dbInfo) -> {
            dataModel.put("packageName", dbInfo.getPackageName());
            dbInfo.getSubList().forEach(tableInfo -> {
                dataModel.put("tableModel", tableInfo);
                OutputStreamWriter out = null;
                try {
                    out = new OutputStreamWriter(new FileOutputStream("D:\\mygit\\javaDbAutoTools\\tempDir\\dao\\Abstract" + StringUtils.toUpperCaseFirstOne(tableInfo.getId()) + "BaseDao.java"));
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
