package com.c2s.dsif;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MatchitApplicantApplication {

	public static void main(String[] args) {
		SpringApplication.run(MatchitApplicantApplication.class, args);
	}

}
