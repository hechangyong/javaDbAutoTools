package com.hecy.jdbctools.generate.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @Author: hecy
 * @Date: 2019/10/21 15:56
 * @Version 1.0
 */
public class StringUtils {

    /**
     * 计算一个字符在字符串中出现的次数
     * @param s
     * @param c
     * @return
     */
    public static int counter(String s,char c){
        int count=0;
        for(int i=0;i<s.length();i++){
            if(s.charAt(i)==c){
                count++;
            }
        }
        return count;
    }

    /**
     * 字符串转换成全小写
     *
     * @param data 带转换的字符串
     * @return
     */
    public static String converToLowerCase(String data) {
        if (data == null) {
            return null;
        }
        return data.toLowerCase();
    }

    public static String uuid(){
        DateFormat format3 = new SimpleDateFormat("yyyyMMddHHmmss");
        String uuid = UUID.randomUUID().toString() + format3.format(new Date());
        return uuid;
    }
    /**
     * 将字符串首字母大写
     * @param s
     * @return
     */
    public static String toUpperCaseFirstOne(String s) {
        if (s == null) {
            return null;
        }
        if (Character.isUpperCase(s.charAt(0))) {
            return s;
        } else {
            return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
        }
    }

    public static void main(String[] args) {
        System.out.println(underlineToHump("abc_d_ss"));
    }

    /***
     * 下划线命名转为驼峰命名
     *
     * @param para
     *        下划线命名的字符串
     */

    public static String underlineToHump(String para) {
        if (para == null) {
            return null;
        }
        StringBuilder result = new StringBuilder();
        String a[] = para.split("_");
        for (String s : a) {
            if (result.length() == 0) {
                result.append(s.toLowerCase());
            } else {
                result.append(s.substring(0, 1).toUpperCase());
                result.append(s.substring(1).toLowerCase());
            }
        }
        return result.toString();
    }


    /***
     * 驼峰命名转为下划线命名
     *
     * @param para
     *        驼峰命名的字符串
     */

    public static String HumpToUnderline(String para) {
        if (para == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder(para);
        //偏移量，第i个下划线的位置是 当前的位置+ 偏移量（i-1）,第一个下划线偏移量是0
        int temp = 0;
        for (int i = 0; i < para.length(); i++) {
            if (Character.isUpperCase(para.charAt(i))) {
                sb.insert(i + temp, "_");
                temp += 1;
            }
        }
        return sb.toString().toLowerCase();
    }


}
