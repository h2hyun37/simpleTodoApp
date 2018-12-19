package com.h2hyun37.todoApp.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.h2hyun37.todoApp")
@EntityScan(basePackages = {"com.h2hyun37.todoApp"})
public class ApplicationRootConfiguration {
	@Bean
	public ObjectMapperFactoryBean objectMapperFactoryBean() {
		return new ObjectMapperFactoryBean();
	}
}
