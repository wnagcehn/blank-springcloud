package com.comic.blankfeignconsumer.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.comic.blankfeignconsumer.exception.ExceptionUtil;
import com.comic.blankfeignconsumer.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author wangchen870
 *         createAt 2019/5/27
 *         updateAt 2019/5/27
 */
@RestController
public class UserController {

    @Resource
    private IUserService userService;

    @SentinelResource(value = "resource", blockHandler = "handleException", blockHandlerClass = {ExceptionUtil.class})
    @GetMapping("/member/{user}")
    public String getUser(@PathVariable("user") String user){
        return userService.getUsername(user);
    }

}
 
