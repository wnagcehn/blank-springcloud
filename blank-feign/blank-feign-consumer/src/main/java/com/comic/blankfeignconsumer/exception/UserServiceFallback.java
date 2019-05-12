package com.comic.blankfeignconsumer.exception;

import com.comic.blankfeignconsumer.service.IUserService;
import org.springframework.stereotype.Component;

@Component
public class UserServiceFallback implements IUserService{

    @Override
    public String getUsername(String name) {
        return "Error";
    }
}
