package com.h2hyun37.todoApp.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

@Configuration
public class WebMvcConfiguration extends WebMvcConfigurationSupport {
	@Autowired
	private ObjectMapper objectMapper;

	@Override
	protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		super.configureMessageConverters(converters);

		converters.add(new MappingJackson2HttpMessageConverter(objectMapper));
	}

	@Override
	protected void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		super.addArgumentResolvers(argumentResolvers);

		argumentResolvers.add(getPageableResolver());
	}

	private PageableHandlerMethodArgumentResolver getPageableResolver() {
		PageableHandlerMethodArgumentResolver resolver = new PageableHandlerMethodArgumentResolver();

		resolver.setFallbackPageable(PageRequest.of(0,100));
		return resolver;
	}
}
