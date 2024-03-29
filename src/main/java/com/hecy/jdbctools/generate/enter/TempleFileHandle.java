package com.hecy.jdbctools.generate.enter;

import com.hecy.jdbctools.generate.common.GlobalXmlData;
import com.hecy.jdbctools.generate.utils.DateUtils;
import com.hecy.jdbctools.generate.utils.StringUtils;
import com.hecy.jdbctools.generate.xmlhandle.handle.DataDictionaryXmlHandle;
import freemarker.template.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.xml.crypto.Data;
import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.hecy.jdbctools.generate.utils.FilleUtils.getPathForLinux;

/**
 * @Author: hecy
 * @Date: 2019/10/18 17:51
 * @Version 1.0
 */
@Component
public class TempleFileHandle {
    static Configuration cfg = new Configuration(new Version("2.3.23"));
    private static Logger log = LoggerFactory.getLogger(TempleFileHandle.class);


    @Autowired
    DataDictionaryXmlHandle dataDictionaryXmlHandle;

    @Value("${generateJavaBasePath}")
    String generateJavaBasePath;

    @Value("${templatePath}")
    String templatePath;

    String currentXmlFileName = null;

    public TempleFileHandle initData(InputStream inputStream, String currentXmlFileName) {
        this.currentXmlFileName = currentXmlFileName;
        dataDictionaryXmlHandle.loadXmlnew(inputStream);
        return this;
    }

    public void generateJavaFile() {
        try {
            genJavaFile("pojo_new.ftl", "pojo");
            genJavaFile("AbstractBaseDao_new.ftl", "abstractdao");
            genJavaFile("Dao.ftl", "dao");
            genJavaFile("IBaseDao.ftl", "idao");

            genSqlFile("create_Sql.ftl", "sql");

            genConfigFile("dataSourceConf.ftl", "config");
            genPageFile("page_pojo.ftl", "pojo");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            GlobalXmlData.msrsDbInfosMapper.clear();
        }
    }

    private void genSqlFile(String temlateFile, String fileType) {
        Map<String, String> dataModel = new HashMap<>(16);

        try {
            genAutoFile(temlateFile, fileType, dataModel, null);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }

    }

    private void genPageFile(String templateFile, String fileType) {
        Map<String, String> dataModel = new HashMap<>(16);
        dataModel.put("author", " auto");
        dataModel.put("date", new Date().toString());
        dataModel.put("pageName", "PageList");
        try {
            genAutoFile(templateFile, fileType, dataModel, "PageList");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }

    private void genConfigFile(String templateFile, String fileType) {
        Map<String, String> dataModel = new HashMap<>(16);
        dataModel.put("author", " auto");
        dataModel.put("date", new Date().toString());
        dataModel.put("connectionProperties", "${spring.datasource.connectionProperties}");
        dataModel.put("driverClassName", "${spring.datasource.driverClassName}");
        dataModel.put("url", "${spring.datasource.url}");
        dataModel.put("username", "${spring.datasource.username}");
        dataModel.put("password", "${spring.datasource.password}");
        try {
            genAutoFile(templateFile, fileType, dataModel, null);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }

    }

    private void genAutoFile(String templateFile, String fileType, Map dataModel, String tableId) throws IOException, TemplateException {
        Template template = getTemplate(templateFile);
        GlobalXmlData.msrsDbInfosMapper.forEach((dbName, dbInfo) -> {
            String packageName = dbInfo.getPackageName();
            dataModel.put("packageName", packageName);
            dataModel.put("tableModels", dbInfo.getSubList());
            OutputStreamWriter out = null;
            try {
                out = out = writerFile(packageName, fileType, tableId);
                template.process(dataModel, out);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }


    /**
     * @param templateFile
     * @param fileType
     */
    public void genJavaFile(String templateFile, String fileType) {
        log.info("开始生成dao java文件");
        Template template = getTemplate(templateFile);
        Map dataModel = new HashMap<String, Object>(16);
        GlobalXmlData.msrsDbInfosMapper.forEach((dbName, dbInfo) -> {
            String packageName = dbInfo.getPackageName();
            dataModel.put("packageName", packageName);
            log.info("用户输入的包名：{}", packageName);
            dbInfo.getSubList().forEach(tableInfo -> {
                dataModel.put("tableModel", tableInfo);
                OutputStreamWriter out = null;
                try {
                    out = writerFile(packageName, fileType, tableInfo.getId());
                    template.process(dataModel, out);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        out.flush();
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        });
    }

    /**
     * 写文件
     *
     * @param packageName 用户配置的包名,"
     * @param fileType    需要生成的java文件类型 "pojo,dao,idao,config"
     * @param tableId     表Id
     */
    public OutputStreamWriter writerFile(String packageName, String fileType, String tableId) throws FileNotFoundException {
        OutputStreamWriter out = null;
        String packagePath = getPackagePath(packageName);
        String finalFileName = null;
        String finalFilePath = null;
        String baseJavaPath = generateJavaBasePath + File.separator + DateUtils.getFormatString("yyyyMMdd") + File.separator + packagePath;
        switch (fileType) {
            case "pojo": {
                finalFilePath = baseJavaPath + "pojo" + File.separator;
                finalFileName = StringUtils.toUpperCaseFirstOne(tableId) + ".java";
                break;
            }
            case "abstractdao": {
                finalFilePath = baseJavaPath + "dao" + File.separator + "baseDao" + File.separator;
                finalFileName = "Abstract" + StringUtils.toUpperCaseFirstOne(tableId) + "BaseDao.java";
                break;
            }
            case "dao": {
                finalFilePath = baseJavaPath + "dao" + File.separator;
                finalFileName = StringUtils.toUpperCaseFirstOne(tableId) + "BaseDao.java";
                break;
            }
            case "idao": {
                finalFilePath = baseJavaPath + "dao" + File.separator + "baseDao" + File.separator;
                finalFileName = "IBaseDao.java";
                break;
            }
            case "config": {
                finalFilePath = baseJavaPath + "config" + File.separator;
                finalFileName = "DataSourceConfig.java";
                break;
            }
            case "sql": {
                finalFilePath = baseJavaPath;
                finalFileName = "db.sql";
                break;
            }
            default:
                finalFilePath = null;
                finalFileName = null;
                break;
        }
        File file = new File(finalFilePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        out = new OutputStreamWriter(new FileOutputStream(new File(finalFilePath + finalFileName)));
        return out;
    }

    private String getPackagePath(String packageName) {
        if (packageName == null) {
            return null;
        }
        String aa[] = packageName.split("\\.");
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < aa.length; i++) {
            sb.append(aa[i].trim() + File.separator);
        }
        return sb.toString();
    }


    private Template getTemplate(String ftlfilename) {
        try {
            cfg.setDirectoryForTemplateLoading(new File(getPathForLinux(templatePath)));
            return cfg.getTemplate(ftlfilename);
        } catch (IOException e) {
            log.error("获取TEMPLATE文件出现异常。" + e.getMessage(), e);
            return null;
        }
    }

}
