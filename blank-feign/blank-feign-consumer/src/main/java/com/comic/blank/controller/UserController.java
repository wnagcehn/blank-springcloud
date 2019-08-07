package com.comic.blank.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.comic.blank.exception.ExceptionUtil;
import com.comic.blank.service.IUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author wangchen
 *         createAt 2019/5/27
 *         updateAt 2019/5/27
 */
@RestController
public class UserController {

    @Resource
    private IUserService userService;

//    @SentinelResource(value = "resource", blockHandler = "handleException", blockHandlerClass = {ExceptionUtil.class})
    @GetMapping("/member/{user}")
    public String getUser(@PathVariable("user") String user){
        return userService.getUsername(user);
    }

}
 
