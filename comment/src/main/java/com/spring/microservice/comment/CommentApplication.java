package com.spring.microservice.comment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableEurekaClient
@ComponentScan("com.spring.microservice.comment")
public class CommentApplication {

	public static void main(String[] args) {
		SpringApplication.run(CommentApplication.class, args);
	}

	@Configuration
	class RestTemplateConfig {

		@LoadBalanced
		@Bean
		RestTemplate loadBalanced() {
			return new RestTemplate();
		}

		@Primary
		@Bean
		RestTemplate restTemplate() {
			return new RestTemplate();
		}
	}
}
