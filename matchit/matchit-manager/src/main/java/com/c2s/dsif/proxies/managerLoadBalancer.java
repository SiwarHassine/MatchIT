package com.c2s.dsif.proxies;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;

import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//import feign.Feign;

@Configuration
@LoadBalancerClient("calculator")
public class managerLoadBalancer {
	
	
//	@LoadBalanced
	//@Bean
	//public Feign.Builder feignBuilder(){
		//return Feign.builder();
	//}

}
