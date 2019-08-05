package com.comic.blankspringcloudproducer.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户服务注册
 *
 * @author wangchen870
 *         createAt 2019/4/27
 *         updateAt 2019/4/27
 */


@RestController
public class UserController {

    @RequestMapping(value = "/getUser/{username}", method = RequestMethod.GET)
    public String echo(@PathVariable("username") String username) {
        return "Hello Nacos Provider:" + username;
    }
}
 
