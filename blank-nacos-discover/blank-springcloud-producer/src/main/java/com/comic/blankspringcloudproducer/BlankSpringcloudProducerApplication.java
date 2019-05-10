package com.comic.blankspringcloudproducer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@SpringBootApplication
@EnableDiscoveryClient
public class BlankSpringcloudProducerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlankSpringcloudProducerApplication.class, args);
	}

}
