package com.comic.blank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class BlankRedisApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlankRedisApplication.class, args);
	}

}
