package com.comic.blankconfig.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
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

// 配置自动更新
@RefreshScope
@Slf4j
@RestController
public class ConfigController {

    @Value("${username:null}")
    private String username;

    @RequestMapping(value = "/getUser", method = RequestMethod.GET)
    public String getUsername(){
        log.info("username:"+username);
        return "Hello," + username;
    }
}
 
