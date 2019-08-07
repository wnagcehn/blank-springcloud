package com.comic.blank.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserProviderController {

    @RequestMapping(value = "/getUsername", method = RequestMethod.GET)
    public String getUsername(@RequestParam("name") String name){
        return  "Hello, " + name;
    }

}
