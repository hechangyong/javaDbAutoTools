package com.hecy.jdbctools.generate.utils;


import org.springframework.cglib.beans.BeanMap;

import java.util.HashMap;
import java.util.Map;

public class MapUtil {
    public static Map<String, Object> removeEmpty(Map<String, Object> map) {
        Map<String, Object> result = new HashMap<>();
        for (String key : map.keySet()) {
            Object value = map.get(key);
            if (!isNull(value)) {
                result.put(key, value);
            }
        }
        return result;
    }

    public static final String EMPTY = "";

    public static boolean isNull(Object str) {
        return str == null || EMPTY.equals(str + EMPTY) || "null".equals(str);
    }


    /**
     * 将map装换为javabean对象
     *
     * @param map
     * @param bean
     * @return
     */
    public static <T> T mapToBean(Map<String, String> map, T bean) {
        BeanMap beanMap = BeanMap.create(bean);
        beanMap.putAll(map);
        return bean;
    }


}
