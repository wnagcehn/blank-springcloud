package com.comic.blank.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.comic.blank.exception.ExceptionUtil;
import com.comic.blank.service.IUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wangchen
 *         createAt 2019/5/27
 *         updateAt 2019/5/27
 */
@RestController
public class UserController {

    @Resource
    private IUserService userService;

    @SentinelResource(value = "resource", blockHandler = "handleException", blockHandlerClass = {ExceptionUtil.class})
    @GetMapping("/member/{user}")
    public Map<String, Object> getUser(@PathVariable("user") String user){
        String result = userService.getUsername(user);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("result", result);
        return map;
    }

}
 
