package com.Test;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import reactor.netty.http.client.HttpClient;

@Configuration
public class WebClientConfig {
	  @Bean
	    public WebClient webClient() {
	        HttpClient httpClient = HttpClient.create()
	                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 500)
	                .responseTimeout(Duration.ofMillis(500))
	                .doOnConnected(conn -> 
	                    conn.addHandlerLast(new ReadTimeoutHandler(500, TimeUnit.MILLISECONDS))
	                        .addHandlerLast(new WriteTimeoutHandler(500, TimeUnit.MILLISECONDS)));

	        ExchangeStrategies strategies = ExchangeStrategies.builder()
	                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(2 * 1024 * 1024)) // 2MB
	                .build();

	        return WebClient.builder()
	                .clientConnector(new ReactorClientHttpConnector(httpClient))
	                .exchangeStrategies(strategies)
	                .build();
	    }
}
