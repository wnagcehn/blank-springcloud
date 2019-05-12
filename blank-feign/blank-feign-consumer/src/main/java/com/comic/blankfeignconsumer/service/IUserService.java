package com.comic.blankfeignconsumer.service;

import com.comic.blankfeignconsumer.exception.UserServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="feign-provider",fallback=UserServiceFallback.class)
public interface IUserService {

    @RequestMapping(value = "/feign/getUsername", method = RequestMethod.GET)
    String getUsername(@RequestParam("name") String name);

}
