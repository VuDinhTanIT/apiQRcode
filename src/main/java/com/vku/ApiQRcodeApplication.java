package com.vku;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;

@SpringBootApplication
public class ApiQRcodeApplication {
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("home");
	}
	public static void main(String[] args) {
		SpringApplication.run(ApiQRcodeApplication.class, args);
		System.out.println("ok");
	}

}
