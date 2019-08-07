package com.comic.blank.util;

import com.alibaba.fastjson.JSON;

/**
 * @author wangchen
 * createAt 2019/5/28
 * updateAt 2019/5/28
 */
public class JsonUtil {

    public static  String beanToJson(Object o){
        return JSON.toJSONString(o);
    }
    //parse an object from
    public static <T> T jsonToBean(String json,Class<T> cls){
        return JSON.parseObject(json, cls);
    }

}
 
