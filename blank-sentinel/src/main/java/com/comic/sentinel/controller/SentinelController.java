package com.comic.sentinel.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.comic.sentinel.exception.ExceptionUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * sentinel测试类
 *
 * @author wangchen870
 *         createAt 2019/4/28
 *         updateAt 2019/4/28
 */
@RestController
public class SentinelController {

    @SentinelResource(value = "hello", blockHandler = "handleException", blockHandlerClass = {ExceptionUtil.class})
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public Map<String,Object> hello(){
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("method","hello");
        map.put("msg","自定义限流逻辑处理");
        return  map;
    }

}
 
