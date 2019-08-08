package com.comic.blank.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.comic.blank.service.IDemoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author wangchen
 * createAt 2019/8/8
 * updateAt 2019/8/8
 */
@RestController
public class DemoController {

    @Resource
    @Reference(version = "1.0.0", check = true)
    private IDemoService demoService;

    @GetMapping("/demo/hello")
    private String demo(){
        return demoService.hello();
    }

}
 
