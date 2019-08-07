/*
 * Copyright (c) 2017, Jiuye SCM and/or its affiliates. All rights reserved.
 * FileName: JsonUtils.java
 * Author:   ningyu
 * Date:     2017年3月6日
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.comic.common.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;

/**
 * Json转换
 */
public class JsonUtils {

    private static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);

    private static final Gson gson = new GsonBuilder().create();

    public static String toJson(Object obj) {
        return gson.toJson(obj);
    }

    public static String toJson(Object obj, Type type) {
        return gson.toJson(obj, type);
    }

    public static <T> T formJson(String json, Class<T> classOfT) {
        try {
            return gson.fromJson(json, classOfT);
        } catch (JsonSyntaxException e) {
            logger.error("json to class{} is error!{}", classOfT.getName(), e);
        }
        return null;
    }

    public static <T> T formJson(String json, Type type) {
        try {
            return gson.fromJson(json, type);
        } catch (JsonSyntaxException e) {
            logger.error("json to class{} is error!{}", type.getClass().getName(), e);
        }
        return null;
    }
}
