package com.comic.blankspringcloudconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
public class BlankSpringcloudConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlankSpringcloudConsumerApplication.class, args);
    }

    //注册一个RestTemplate实例用以发送请求，给RestTemplate实例添加@LoadBalanced注解，开启与Ribbon的集成用于负载均衡
    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
