package com.bigdata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import  org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
//@EnableDiscoveryClient
//@EnableFeignClients
//@EnableCircuitBreaker
public class BigDataProcessorApplication {

	public static void main(String[] args) {
		SpringApplication.run(BigDataProcessorApplication.class, args);
	}

}
