package com.comic.blankfeignconsumer.controller;

import com.comic.blankfeignconsumer.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangchen870
 *         createAt 2019/5/27
 *         updateAt 2019/5/27
 */
@RestController
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping("/getUser")
    public String getUser(){
        return userService.getUsername("wangchen");
    }

}
 
