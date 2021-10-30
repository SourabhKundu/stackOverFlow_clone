package com.mountblue.StackOverFlow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan({"com.mountblue.StackOverFlow.model"})
public class StackOverFlowApplication {

	public static void main(String[] args) {
		SpringApplication.run(StackOverFlowApplication.class, args);
		System.out.println("hello world");
	}
}
