package com.c2s.dsif;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
@EnableConfigServer
@SpringBootApplication
public class MatchitConfigServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MatchitConfigServerApplication.class, args);
	}
}
