package com.comic.blankspringcloudconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
public class BlankSpringcloudConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlankSpringcloudConsumerApplication.class, args);
	}

}
