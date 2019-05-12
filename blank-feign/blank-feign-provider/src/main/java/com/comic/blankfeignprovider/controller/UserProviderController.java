package com.comic.blankfeignprovider.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserProviderController {

    @RequestMapping(value = "/getUsername")
    public String getUsername(@RequestParam("name") String name){
        return "Hello," + name;
    }
}
