package com.hecy.jdbctools.generate.xmlhandle.handle;

import com.hecy.jdbctools.generate.bean.DataSourceInfo;
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
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @Author: hecy
 * @Date: 2019/7/4 10:58
 * @Version 1.0
 */
@Component
public class DataSourceXmlHandle implements HandleXmlBase<DataSourceInfo> {
    private static Logger log = LoggerFactory.getLogger(DataSourceXmlHandle.class);

    public List<DataSourceInfo> dataSourceslist;

    final Object key = new Object();

    @Value("${xml.model.dsxmlPath}")
    private String dsxmlPath;
//

    @Autowired
    FileConfig fileConfig;


    @Override
    public void generateXml() {
        log.info("请手动编辑");
    }

    /**
     * 更新数据源数据
     */
    @Override
    public void updateGlobalXmlData() {
        log.info("更新数据源数据。");
        synchronized (key) {
            dataSourceslist.forEach(data -> {
                String key = data.getId();
                if (GlobalXmlData.msrsDatasourceMapper.containsKey(key)) {
                    log.error("配置文件存在重复的数据源ID：{}", key);
                    try {
                        throw new Throwable("配置文件存在重复的数据源ID：" + key);
                    } catch (Throwable throwable) {
                        throwable.printStackTrace();
                    }
                }
                GlobalXmlData.msrsDatasourceMapper.put(key, data);
            });
        }
    }

    @Override
    public void loadXml() {
        log.info("装载XML文件数据：{}", dsxmlPath);
        loadXml(fileConfig.getInputStreamForLinux(dsxmlPath));
    }


    private List<DataSourceInfo> loadXml(InputStream inputStream) {
        dataSourceslist = new ArrayList<>();
        try {
            Document document = Dom4jXmlParse.parse(inputStream);
            Element eles = document.getRootElement();

            Iterator it = eles.elementIterator();
            while (it.hasNext()) {
                Element ele = (Element) it.next();
                DataSourceInfo dataSource = Dom4jXmlParse.loadMetadataElementToBean(ele, DataSourceInfo.class);
                dataSourceslist.add(dataSource);
            }
            log.info("dataSource: {}", dataSourceslist);

        } catch (Exception e) {
            log.error("加载数据源问价出现异常：{}", e.getMessage(), e);
        }
        updateGlobalXmlData();
        return dataSourceslist;
    }

    public static void main(String[] args) {
        Resource resource = new ClassPathResource("design/model");
        try {
            File sourceFile = resource.getFile();
            log.info("sourceFile:{}", sourceFile.getPath());
        } catch (IOException e) {
            e.printStackTrace();
        }

//        new DataSourceXmlHandle().loadXml("D:\\mslife\\code\\msreport\\data_dictionary\\src\\main\\resources\\design\\model\\dataSource.xml");
    }
}
