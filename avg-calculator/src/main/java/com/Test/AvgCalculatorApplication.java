package com.Test;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import reactor.netty.http.client.HttpClient;

@SpringBootApplication
public class AvgCalculatorApplication {

	
	public static void main(String[] args) {
		SpringApplication.run(AvgCalculatorApplication.class, args);
	}
	
	  @Value("${app.third-party.timeout}")
	    private int timeout;
	  
	  
}
