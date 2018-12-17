package com.h2hyun37.todoApp.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
@ComponentScan("com.h2hyun37.todoApp")
@EntityScan(basePackages = {"com.h2hyun37.todoApp"})
public class ApplicationRootConfiguration {
	@Bean
	public ObjectMapperFactoryBean objectMapperFactoryBean() {
		return new ObjectMapperFactoryBean();
	}

	@Bean
	public RestTemplate restTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate;
	}
}
