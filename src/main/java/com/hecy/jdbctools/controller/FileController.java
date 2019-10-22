package com.hecy.jdbctools.controller;


import com.hecy.jdbctools.generate.enter.TempleFileHandle;
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

    @GetMapping("/downloads")
    public void downloads(HttpServletRequest request, HttpServletResponse response) {

        String sourceFilePath = "E:\\mygitCode\\javaDbAutoTools\\tempDir";

        File sourceDir = new File(sourceFilePath);
        File zipFile = new File(sourceFilePath + ".zip");
        ZipOutputStream zos = null;
        try {
            zos = new ZipOutputStream(new FileOutputStream(zipFile));
            String baseDir = "downloadfiles/";
            compress(sourceDir, baseDir, zos);
            download(zipFile, request, response);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (zos != null)
                try {
                    zos.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
        }
    }


    @PostMapping("/d")
    public void d(HttpServletRequest request, HttpServletResponse response) {
        List<File> fileList = new ArrayList<>();
        //根目录
        String rootPath = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession().getServletContext().getRealPath("") + "upload\\";
        //文件地址数组
        String path = "";
        String[] paths = path.split(";");
        for (String string : paths) {
            String s = rootPath + string;
            File f = new File(s);
            if (!f.exists()) continue;
            fileList.add(f);
        }
        if (fileList.size() <= 0) return;
        try {
            //保存的文件名为 当前日期 +随机数
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            String date = sdf.format(new Date());
            String ran = 2 + (int) (Math.random() * (102 - 2)) + "";
            //判断文件夹是否存在
            File f = new File(rootPath);
            if (!f.exists()) f.mkdirs();
            //保存文件
            File file = new File(rootPath + date + ran + ".rar");
            if (!file.exists()) {
                file.createNewFile();
            }
            response.reset();
            //创建文件输出流
            FileOutputStream fous = new FileOutputStream(file);
            /**打包的方法我们会用到ZipOutputStream这样一个输出流, 所以这里我们把输出流转换一下*/
            ZipOutputStream zipOut = new ZipOutputStream(fous);
            zipFile(fileList, zipOut);
            zipOut.close();
            fous.close();

            download(file, request, response);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


    public void download(File f, HttpServletRequest request, HttpServletResponse response) {
        if (!f.exists()) return;
        try {
            String filename = f.getName();
            // 当文件名不是英文名的时候，最好使用url解码器去编码一下，
            filename = URLEncoder.encode(filename, "UTF-8");
            // 将响应的类型设置为图片
            //response.setContentType("image/jpeg");
            response.setHeader("Content-Disposition", "attachment;filename=" + filename);
            // 现在通过IO流来传送数据
            InputStream input = new FileInputStream(f);
            // getServletContext().getResourceAsStream("/testImage.jpg");
            OutputStream output = response.getOutputStream();
            byte[] buff = new byte[1024 * 10];// 可以自己 指定缓冲区的大小
            int len = 0;
            while ((len = input.read(buff)) > -1) {
                output.write(buff, 0, len);
            }
            // 关闭输入输出流
            input.close();
            output.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
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
