package com.h2hyun37.todoApp.configuration;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.InstantDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.ZonedDateTimeSerializer;
import com.h2hyun37.todoApp.constants.Constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.FactoryBean;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

@Slf4j
public class ObjectMapperFactoryBean implements FactoryBean<ObjectMapper> {
	private List<Module> moduleList;

	public ObjectMapperFactoryBean() {
		Jdk8Module jdk8Module = new Jdk8Module();
		JavaTimeModule javaTimeModule = new JavaTimeModule();

		javaTimeModule.addSerializer(
				ZonedDateTime.class,
				new JsonSerializer<ZonedDateTime>() {
					@Override
					public void serialize(ZonedDateTime value, JsonGenerator generator, SerializerProvider serializers) throws IOException {
						if (value != null) {
							value = value.withZoneSameInstant(TimeZone.getDefault().toZoneId());
						}
						new ZonedDateTimeSerializer(Constant.DEFAULT_API_DATE_TIME)
								.serialize(value, generator, serializers);
					}
				});
		javaTimeModule.addDeserializer(
				ZonedDateTime.class,
				new JsonDeserializer<ZonedDateTime>() {
					@Override
					public ZonedDateTime deserialize(JsonParser parser, DeserializationContext context) throws IOException {
						ZonedDateTime zonedDateTime = InstantDeserializer.ZONED_DATE_TIME.deserialize(parser, context);
						if (zonedDateTime == null) {
							return null;
						}
						return zonedDateTime.withZoneSameInstant(TimeZone.getDefault().toZoneId());
					}
				});

		moduleList = new ArrayList<>();
		moduleList.add(jdk8Module);
		moduleList.add(javaTimeModule);
	}

	@Override
	public ObjectMapper getObject() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModules(moduleList);
		objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		objectMapper.disable(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES);
		return objectMapper;
	}

	@Override
	public Class<?> getObjectType() {
		return ObjectMapper.class;
	}

	@Override
	public boolean isSingleton() {
		return false;
	}
}
