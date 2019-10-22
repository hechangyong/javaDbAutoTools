package com.hecy.jdbctools.generate.utils;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @Author: hecy
 * @Date: 2019/10/22 15:05
 * @Version 1.0
 */
public class FilleUtils {


    /**
     * 把接受的全部文件打成压缩包
     *
     * @param files
     * @param outputStream
     */
    public static void zipFile(List files, ZipOutputStream outputStream) {
        int size = files.size();
        for (int i = 0; i < size; i++) {
            File file = (File) files.get(i);
            zipFile(file, outputStream);
        }
    }

    /**
     * 根据输入的文件与输出流对文件进行打包
     *
     * @param inputFile
     * @param ouputStream
     */
    public static void zipFile(File inputFile, ZipOutputStream ouputStream) {
        try {
            if (inputFile.exists()) {
                /**
                 * 如果是目录的话这里是不采取操作的，  * 至于目录的打包正在研究中
                 */
                if (inputFile.isFile()) {
                    FileInputStream IN = new FileInputStream(inputFile);
                    BufferedInputStream bins = new BufferedInputStream(IN, 512);
                    // org.apache.tools.zip.ZipEntry
                    ZipEntry entry = new ZipEntry(inputFile.getName());
                    ouputStream.putNextEntry(entry);
                    // 向压缩文件中输出数据
                    int nNumber;
                    byte[] buffer = new byte[512];
                    while ((nNumber = bins.read(buffer)) != -1) {
                        ouputStream.write(buffer, 0, nNumber);
                    }
                    // 关闭创建的流对象
                    bins.close();
                    IN.close();
                } else {
                    try {
                        File[] files = inputFile.listFiles();
                        for (int i = 0; i < files.length; i++) {
                            zipFile(files[i], ouputStream);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


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

    public static void compress(File f, String baseDir, ZipOutputStream zos){
        if(!f.exists()){
            System.out.println("待压缩的文件目录或文件"+f.getName()+"不存在");
            return;
        }


        File[] fs = f.listFiles();
        BufferedInputStream bis = null;
        //ZipOutputStream zos = null;
        byte[] bufs = new byte[1024*10];
        FileInputStream fis = null;


        try{
            //zos = new ZipOutputStream(new FileOutputStream(zipFile));
            for(int i=0; i<fs.length; i++){
                String fName =  fs[i].getName();
                System.out.println("压缩：" + baseDir+fName);
                if(fs[i].isFile()){
                    ZipEntry zipEntry = new ZipEntry(baseDir+fName);//
                    zos.putNextEntry(zipEntry);
                    //读取待压缩的文件并写进压缩包里
                    fis = new FileInputStream(fs[i]);
                    bis = new BufferedInputStream(fis, 1024*10);
                    int read = 0;
                    while((read=bis.read(bufs, 0, 1024*10))!=-1){
                        zos.write(bufs, 0, read);
                    }
                    //如果需要删除源文件，则需要执行下面2句
                    //fis.close();
                    //fs[i].delete();
                }
                else if(fs[i].isDirectory()){
                    compress(fs[i], baseDir+fName+"/", zos);
                }
            }//end for
        }catch  (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally{
            //关闭流
            try {
                if(null!=bis)
                    bis.close();
                //if(null!=zos)
                //zos.close();
                if(null!=fis)
                    fis.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

    public static void main( String[] args ) throws ParseException
    {

        String sourceFilePath = "E:\\mygitCode\\javaDbAutoTools\\tempFile";

        File sourceDir = new File(sourceFilePath);
        File zipFile = new File(sourceFilePath+".zip");
        ZipOutputStream zos = null;
        try {
            zos = new ZipOutputStream(new FileOutputStream(zipFile));
            String baseDir = "downloadfiles/";
            compress(sourceDir, baseDir, zos);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally{
            if(zos!=null)
                try {
                    zos.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
        }
    }



}
