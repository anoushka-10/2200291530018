package com.Test.Config;

import java.text.SimpleDateFormat;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Configuration
public class Config {
	    @Bean
	    @Primary
	    public ObjectMapper objectMapper() {
	        ObjectMapper objectMapper = new ObjectMapper();
	        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
	        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
	        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
	        return objectMapper;
	    }
}
