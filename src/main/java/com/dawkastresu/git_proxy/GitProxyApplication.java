package com.dawkastresu.git_proxy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.dawkastresu.git_proxy")
public class GitProxyApplication {

	public static void main(String[] args) {
		SpringApplication.run(GitProxyApplication.class, args);
	}

}
