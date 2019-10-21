package com.hecy.jdbctools.controller;


import com.hecy.jdbctools.generate.enter.MainTestCase;
import com.hecy.jdbctools.generate.xmlhandle.handle.DataDictionaryXmlHandle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.ws.Action;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/a")
public class FileController {
    private static Logger log = LoggerFactory.getLogger(FileController.class);

    @Autowired
    MainTestCase mainTestCase;

    @RequestMapping("/ss")
    @ResponseBody
    public String cust_balance_import_todo( @RequestParam("userInfo") MultipartFile file) throws Exception {
        if(file.isEmpty()){//其中这个file自己获取的，别全部都copy完啊
            return "false";
        }
        String fileName = file.getOriginalFilename();//获取文件夹名字
        if(fileName.indexOf("xls")<0){
            return "上传文件类型不符合要求，请确定是 (.xls/.xlsx)后缀的Excel 文件";
        }
        //List<List<Object>> list = ReadExcel.readExcel(file);
        InputStream inputStream = file.getInputStream();
        //这里是重要的

        //这里获取完，根据需要写你的东东 这个list
        inputStream.close();
        return null;
    }

    @PostMapping("/upload")
    @ResponseBody
    public String upload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return "上传失败，请选择文件";
        }

        String fileName = file.getOriginalFilename();
        String filePath = "E:\\mygitCode\\javaDbAutoTools\\tempFile\\";
        File dest = new File(filePath + fileName);
        try {
            file.transferTo(dest);
            mainTestCase.mainTestCase(new FileInputStream(dest));

            log.info("上传成功");
            return "上传成功";
        } catch (IOException e) {
            log.error(e.toString(), e);
        }
        return "上传失败！";
    }
}
