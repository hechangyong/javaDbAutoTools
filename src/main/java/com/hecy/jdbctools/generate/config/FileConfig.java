package com.hecy.jdbctools.generate.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @Author: hecy
 * @Date: 2019/8/16 14:01
 * @Version 1.0
 */
@Component
@Data
public class FileConfig {

    @Value("${xml.model.genpath}")
    private String genXmlPath;

    @Value("${xml.model.ddxmlPath}")
    private String ddxmlPath;

    @Value("${xml.model.dsxmlPath}")
    private String dsxmlPath;

    @Value("${xml.model.opXmlPath}")
    private String opXmlPath;

    @Value("${xml.model.appname}")
    private String appname;

    private String getbasePath() {
        String userDir = System.getProperty("user.dir");
        String separator = System.getProperty("file.separator");
        if (userDir.contains(appname)) {
            return userDir;
        } else {
            return userDir + separator + appname;
        }
    }

    public String getPathForLinux(String pathName) {
        Resource resource = new ClassPathResource(pathName);
        try {
             File sourceFile = resource.getFile();

            return sourceFile.getPath();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    public InputStream getInputStreamForLinux(String pathName) {
        Resource resource = new ClassPathResource(pathName);
        try {
            return resource.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getPath(String pathName) {
        String path = switchPath(pathName);
        String separator = System.getProperty("file.separator");
        if (!path.startsWith(separator)) {
            path = separator + path;
        }
        return getbasePath() + path;
    }

    private String switchPath(String pathName) {
        String path = null;
        switch (pathName) {
            case "genXmlPath":
                path = genXmlPath;
                break;
            case "ddxmlPath":
                path = ddxmlPath;
                break;
            case "dsxmlPath":
                path = dsxmlPath;
                break;
            case "opXmlPath":
                path = opXmlPath;
                break;
            default:
                try {
                    throw new Exception("没有匹配到正确的路径名称。");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
        return path;
    }


    public String getPath4Linux(String replace) {
        return null;
    }
}
