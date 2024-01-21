package com.niit.springCLoudApiGateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SpringCLoudApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCLoudApiGatewayApplication.class, args);
	}

}
