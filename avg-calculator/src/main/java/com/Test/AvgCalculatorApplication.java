package com.Test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class AvgCalculatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(AvgCalculatorApplication.class, args);
	}
	 @Bean
	    public WebClient webClient() {
	        return WebClient.builder()
	                .codecs(configurer -> configurer
	                        .defaultCodecs()
	                        .maxInMemorySize(16 * 1024 * 1024)) 
	                .build();
	    }
}
