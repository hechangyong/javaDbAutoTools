package com.hecy.jdbctools.controller;


import com.hecy.jdbctools.generate.enter.TempleFileHandle;
import com.hecy.jdbctools.generate.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipOutputStream;

import static com.hecy.jdbctools.generate.utils.DateUtils.getFormatString;
import static com.hecy.jdbctools.generate.utils.FilleUtils.*;
import static com.hecy.jdbctools.generate.utils.StringUtils.uuid;

@RestController
@RequestMapping("/a")
public class FileController {
    private static Logger log = LoggerFactory.getLogger(FileController.class);

    @Autowired
    TempleFileHandle templeFileHandle;

    @Value("${fileuploadPath}")
    String fileuploadPath;

    @Value("${generateJavaBasePath}")
    String generateJavaBasePath;

    private String getZipFilePath() {
        return generateJavaBasePath + File.separator + DateUtils.getFormatString("yyyyMMdd");
    }

    @PostMapping("/zip")
    public String zip(HttpServletRequest request, HttpServletResponse response) {
        String zipPath = getZipFilePath();
        File sourceDir = new File(zipPath);
        File zipFile = new File(zipPath + ".zip");
        ZipOutputStream zos = null;
        try {
            zos = new ZipOutputStream(new FileOutputStream(zipFile));
            String baseDir = "downloadfiles/";
            compress(sourceDir, baseDir, zos);
            return "SUCCESS";
//            download(zipFile, request, response);
        } catch (FileNotFoundException e) {
            log.error("下载文件出现异常:{}", e.getMessage(), e);
            return "ERROR";
        } finally {
            if (zos != null) {
                try {
                    zos.close();
                } catch (IOException e) {
                    log.error("关闭流出现异常:{}", e.getMessage());
                }
            }
        }
    }

    @GetMapping("/s")
    public void s() {
        log.info("ssssss");
    }

    @GetMapping("/downloads")
    public void download(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String zipPath = getZipFilePath();
        File f = new File(zipPath + ".zip");
        if (!f.exists()) {
            return;
        }
        InputStream input = null;
        OutputStream output = null;
        try {
            String filename = f.getName();
            // 当文件名不是英文名的时候，最好使用url解码器去编码一下，
            filename = URLEncoder.encode(filename, "UTF-8");
            // 将响应的类型设置为图片
            response.setHeader("Content-Disposition", "attachment;filename=" + filename);
            // 现在通过IO流来传送数据
            input = new FileInputStream(f);
            output = response.getOutputStream();
            // 可以自己 指定缓冲区的大小
            byte[] buff = new byte[1024 * 10];
            int len = 0;
            while ((len = input.read(buff)) > -1) {
                output.write(buff, 0, len);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            // 关闭输入输出流
            input.close();
            output.close();
        }

    }


    @PostMapping("/upload")
    @ResponseBody
    public String upload(@RequestParam("file") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return "上传失败，请选择文件";
        }
        File upload = new File(getPathForLinux(fileuploadPath), getFormatString("yyyyMMdd"));
        if (!upload.exists()) {
            upload.mkdirs();
        }
        String fileName = file.getOriginalFilename();
        log.info("上传文件的原始名称：{}", fileName);
        InputStream in = file.getInputStream();

        File dest = new File(upload + File.separator + uuid() + fileName);
        try {
            file.transferTo(dest);

            templeFileHandle.initData(in, fileName)
                    .generateJavaFile();

            log.info("上传成功");
            return "上传成功";
        } catch (IOException e) {
            log.error(e.toString(), e);
        }
        return "上传失败！";
    }


    public static void main(String[] args) {
        log.info(getPathForLinux("upload\\"));
    }
}
