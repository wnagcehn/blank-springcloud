package com.comic.blankfeignconsumer.controller;

import com.comic.blankfeignconsumer.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping("/getUser")
    private String getUser(){
        return userService.getUsername("wangchen");
    }

}