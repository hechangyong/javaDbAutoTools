package com.hecy.jdbctools.controller;


import com.hecy.jdbctools.generate.enter.TempleFileHandle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import static com.hecy.jdbctools.generate.utils.DateUtils.getFormatString;
import static com.hecy.jdbctools.generate.utils.FilleUtils.getPathForLinux;
import static com.hecy.jdbctools.generate.utils.StringUtils.uuid;

@RestController
@RequestMapping("/a")
public class FileController {
    private static Logger log = LoggerFactory.getLogger(FileController.class);

    @Autowired
    TempleFileHandle templeFileHandle;

    @Value("${fileuploadPath}")
    String fileuploadPath;

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
        log.info("上传文件的原始名称：{}",fileName);
        InputStream in = file.getInputStream();

        File dest = new File(upload + File.separator + uuid() + fileName);
        try {
            file.transferTo(dest);

            templeFileHandle.initData(in)
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
