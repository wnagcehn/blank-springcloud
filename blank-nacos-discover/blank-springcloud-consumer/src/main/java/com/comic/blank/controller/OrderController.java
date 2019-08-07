package com.comic.blank.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class OrderController {

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping(value = "/getOrder")
    public Map<String, Object> getOrder() {
        Map<String, Object> order = new HashMap<String, Object>();
        order.put("username", queryUserName());
        order.put("money", 100.00);
        return order;
    }

    private String queryUserName() {

        try {
            return restTemplate.getForObject("http://nacos-producer/user/getUser", String.class);
        } catch (Exception e) {
            log.error("query user error", e);
        }
        return null;
    }
}
