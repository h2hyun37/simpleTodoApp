package com.h2hyun37.todoApp.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.resource.PathResourceResolver;

import java.util.List;

@Configuration
public class WebMvcConfiguration extends WebMvcConfigurationSupport {
	private static final String[] RESOURCE_LOCATIONS = {
			"classpath:/static/"
	};

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

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry
				.addResourceHandler("/static/**")
				.addResourceLocations(RESOURCE_LOCATIONS)
				.setCachePeriod(3600)
				.resourceChain(true)
				.addResolver(new PathResourceResolver());
	}


	private PageableHandlerMethodArgumentResolver getPageableResolver() {
		PageableHandlerMethodArgumentResolver resolver = new PageableHandlerMethodArgumentResolver();

		resolver.setFallbackPageable(PageRequest.of(0,100));
		return resolver;
	}
}
