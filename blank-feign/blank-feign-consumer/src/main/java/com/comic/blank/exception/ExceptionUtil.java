package com.comic.blank.exception;

import com.alibaba.csp.sentinel.slots.block.BlockException;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wangchen
 * createAt 2019/8/5
 * updateAt 2019/8/5
 */
public class ExceptionUtil {

    public static Map<String, Object> handleException(String user, BlockException e) {
        Map<String,Object> map=new HashMap<>();
        System.out.println("Oops: " + e.getClass().getCanonicalName());
        map.put("Oops",e.getClass().getCanonicalName());
        map.put("msg","通过@SentinelResource注解配置限流埋点并自定义处理限流后的逻辑");
        return  map;
    }

}
 
