package com.comic.sentinel.exception;

import com.alibaba.csp.sentinel.slots.block.BlockException;

import java.util.HashMap;
import java.util.Map;

public class ExceptionUtil {

    //降级
    public static Map<String,Object> handleException(BlockException ex) {
        Map<String,Object> map=new HashMap<String,Object>();
        System.out.println("Oops: " + ex.getClass().getCanonicalName());
        map.put("Oops",ex.getClass().getCanonicalName());
        map.put("msg","扛不住了啊....（通过@SentinelResource注解配置限流埋点并自定义处理限流后的逻辑）");
        return  map;
    }

}
