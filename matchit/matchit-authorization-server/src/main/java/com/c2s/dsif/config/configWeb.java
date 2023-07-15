package com.c2s.dsif.config;

import java.util.List;


import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Configuration
public class configWeb implements WebMvcConfigurer {

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
	    MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
	    ObjectMapper objectMapper = converter.getObjectMapper();
	    objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
	    converters.add(converter);
	}
}






