package com.comic.blank.rediscallback.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author wangchen
 * createAt 2019/7/17
 * updateAt 2019/7/17
 */
public class CacheUtils {

    /**
     * log
     */
    private static Logger logger = LoggerFactory.getLogger(CacheUtils.class);

    private static String DOT = ".";

    public static void setKeySeparator(String keySeparator) {
        if (StringUtils.isNotBlank(keySeparator)) {
            DOT = keySeparator;
        }
    }

    public static String getKeyByNamespace(String key, String nameSpace) {
        if (StringUtils.isNotBlank(nameSpace)) {
            return nameSpace + DOT + key;
        }
        return key;
    }

}
 
