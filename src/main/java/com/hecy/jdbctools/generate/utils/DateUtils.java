package com.hecy.jdbctools.generate.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: hecy
 * @Date: 2019/10/22 14:44
 * @Version 1.0
 */
public class DateUtils {


    public static String getFormatString(String pattern){
        //"yyyyMMdd"
        DateFormat format = new SimpleDateFormat(pattern);
        return format.format(new Date());
    }

}
