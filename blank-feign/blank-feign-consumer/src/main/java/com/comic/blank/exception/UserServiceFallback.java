package com.comic.blank.exception;

import com.comic.blank.service.IUserService;
import org.springframework.stereotype.Component;

@Component
public class UserServiceFallback implements IUserService{

    @Override
    public String getUsername(String name) {
        return "Error";
    }
}
