package com.c2s.dsif;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@EnableFeignClients
@SpringBootApplication
@ComponentScan(basePackages = "com.c2s.dsif")
public class MatchitCalculatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(MatchitCalculatorApplication.class, args);
	}

}
