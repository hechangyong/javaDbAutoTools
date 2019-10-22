package com.hecy.jdbctools.generate.utils;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @Author: hecy
 * @Date: 2019/10/22 15:05
 * @Version 1.0
 */
public class FilleUtils {


    public static String getPathForLinux(String pathName) {
        if (pathName == null) {
            return null;
        }
        Resource resource = new ClassPathResource(pathName);
        try {
            File sourceFile = resource.getFile();
            if (!sourceFile.exists()) {
                sourceFile.mkdirs();
            }
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


}
