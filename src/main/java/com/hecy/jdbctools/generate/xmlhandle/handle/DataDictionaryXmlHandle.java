package com.hecy.jdbctools.generate.xmlhandle.handle;

import com.hecy.jdbctools.generate.bean.DbInfo;
import com.hecy.jdbctools.generate.bean.FieldInfo;
import com.hecy.jdbctools.generate.bean.TableInfo;
import com.hecy.jdbctools.generate.bean.base.BaseModel;
import com.hecy.jdbctools.generate.bean.base.SubModel;
import com.hecy.jdbctools.generate.common.GlobalXmlData;
import com.hecy.jdbctools.generate.config.FileConfig;
import com.hecy.jdbctools.generate.utils.Dom4jXmlParse;
import com.hecy.jdbctools.generate.xmlhandle.HandleXmlBase;
import org.dom4j.Document;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import java.io.InputStream;
import java.util.*;

/**
 * @Author: hecy
 * @Date: 2019/7/4 15:32
 * @Version 1.0
 */
@Component
public class DataDictionaryXmlHandle implements HandleXmlBase<DbInfo> {

    private static Logger log = LoggerFactory.getLogger(DataDictionaryXmlHandle.class);

    /**
     * key: key数据源ID
     * value: 数据字典
     */
    public List<DbInfo> glodbInfos;

    final Object dataDictionaryKey = new Object();

    @Value("${xml.model.genpath}")
    private String genXmlPath;
    //
    @Value("${xml.model.ddxmlPath}")
    private String ddxmlPath;

    @Autowired
    FileConfig fileConfig;


    private String transformTag(String type) {
        String tag;
        switch (type) {
            case "TIMESTAMP":
            case "DATE":
            case "TIME":
            case "DATETIME":
                tag = "date";
                break;
            case "INT":
            case "TINYINT":
            case "SMALLINT":
            case "INT UNSIGNED":
            case "DOUBLE":
            case "BIGINT UNSIGNED":
            case "BIGINT":
            case "DECIMAL":
                tag = "number";
                break;
            case "VARCHAR":
            case "CHAR":
            case "MEDIUMTEXT":
            case "TEXT":
            case "LONGTEXT":
                tag = "character";
                break;
            default:
                log.info("未知数据类型: {}", type);
                tag = "character";
        }
        return tag;
    }


    @Override
    public void generateXml() {

    }

    @Override
    public void loadXml() {
        GlobalXmlData.msrsDatasourceMapper.forEach((dbid, dbinfo) -> {
            log.info("开始加载{}文件", dbid);
            loadXml(dbid, fileConfig.getInputStreamForLinux(ddxmlPath.replace("{}", dbid)));
        });
    }

    public static void main(String[] args) {
        try {
//            log.info(ResourceUtils.getURL("classpath:").getPath());//在linux下面没有用
//            log.info(ClassUtils.getDefaultClassLoader().getResource("").getPath());
            log.info(System.getProperty("user.dir"));
//            String f = System.getProperty("user.dir");
            //            File dssd = ResourceUtils.getFile("classpath:dd");
//            if (!filemkdir.exists()) {
//                filemkdir.mkdir();
//            }
//            log.info(dssd.getName());
//            log.info(filemkdir.getName());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<DbInfo> loadXml(String dataDourceId, InputStream in) {
        log.info("=====开始加载【dataSource】XML=====");
        glodbInfos = new ArrayList<>();
        List<DbInfo> subList = new ArrayList<>();
        try {
            Document document = Dom4jXmlParse.parse(in);
            Element eles = document.getRootElement();
            Iterator it = eles.elementIterator();
            List<DbInfo> dbInfos = new ArrayList<>();
            while (it.hasNext()) {
                Element dbele = (Element) it.next();
                Iterator itt = dbele.elementIterator();
                DbInfo dbInfo = Dom4jXmlParse.loadMetadataElementToBean(dbele, DbInfo.class);
                List<TableInfo> tableInfos = new ArrayList<>();
                while (itt.hasNext()) {
                    TableInfo tableInfo = assembleBean((Element) itt.next(), TableInfo.class, FieldInfo.class);
                    tableInfos.add(tableInfo);
                }
                dbInfo.setSubList(tableInfos);
                dbInfos.add(dbInfo);
            }
            glodbInfos = dbInfos;
            log.info("dataSource: {}", glodbInfos);

        } catch (Exception e) {
            log.error("加载数据 源问价出现异常：{}", e.getMessage(), e);
        }
        updateGlobalXmlData();
        return subList;
    }


    private <T extends BaseModel, B extends SubModel> T assembleBean(Element childele, Class<T> tClass, Class<B> subClass) {
        Iterator ittt = childele.elementIterator();
        T tableInfo = Dom4jXmlParse.loadMetadataElementToBean(childele, tClass);
        List<B> fieldList = new ArrayList<>();
        while (ittt.hasNext()) {
            Element childSubele = (Element) ittt.next();
            B sub = Dom4jXmlParse.loadMetadataElementToBean(childSubele, subClass);
            fieldList.add(sub);
        }
        tableInfo.setSubList(fieldList);
        return tableInfo;
    }


    @Override
    public void updateGlobalXmlData() {
        synchronized (dataDictionaryKey) {
            glodbInfos.forEach(dbinfos -> {
                if (GlobalXmlData.msrsDbInfosMapper.containsKey(dbinfos.getDbName())) {
                    log.error("配置文件存在重复的数据源ID ：{}", dbinfos.getDbName());
                    try {
                        throw new Throwable("配置文件存在重复的数据源ID：" + dbinfos.getDbName());
                    } catch (Throwable throwable) {
                        throwable.printStackTrace();
                    }
                }
                GlobalXmlData.msrsDbInfosMapper.put(dbinfos.getDbName(), dbinfos);
            });


        }
    }
}
