package com.Test.Service;



import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.Test.Model.AverageResponse;
import com.Test.Model.NumbersResponse;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class NumberService {

    private final WebClient webClient;
    private final int windowSize;
    private final String baseUrl;
    private final int timeout;
    private final LinkedList<Integer> numberWindow = new LinkedList<>();

    public NumberService(
            WebClient webClient,
            @Value("${app.window.size}") int windowSize,
            @Value("${app.third-party.base-url}") String baseUrl,
            @Value("${app.third-party.timeout}") int timeout) {
        this.webClient = webClient;
        this.windowSize = windowSize;
        this.baseUrl = baseUrl;
        this.timeout = timeout;
    }

    public AverageResponse processNumberRequest(String numberId) {
        List<Integer> previousState = new ArrayList<>(numberWindow);
        
        NumbersResponse response = fetchNumbers(numberId);
        List<Integer> receivedNumbers = response != null ? response.getNumbers() : Collections.emptyList();
        
        addUniqueNumbersToWindow(receivedNumbers);
        
        List<Integer> currentState = new ArrayList<>(numberWindow);
        double average = calculateAverage(currentState);
        
        return AverageResponse.builder()
                .windowPrevState(previousState)
                .windowCurrState(currentState)
                .numbers(receivedNumbers)
                .avg(average)
                .build();
    }

    private NumbersResponse fetchNumbers(String numberId) {
        String endpoint;
        switch (numberId) {
            case "p":
                endpoint = "/primes";
                break;
            case "f":
                endpoint = "/fibo";
                break;
            case "e":
                endpoint = "/even";
                break;
            case "r":
                endpoint = "/rand";
                break;
            default:
                log.error("Invalid number id: {}", numberId);
                return null;
        }

        String fullUrl = baseUrl + endpoint;
        log.info("Calling third-party API: {}", fullUrl);

        try {
            NumbersResponse response = webClient.get()
                    .uri(fullUrl)
                    .retrieve()
                    .bodyToMono(NumbersResponse.class)
                    .timeout(Duration.ofMillis(timeout))
                    .doOnSuccess(r -> {
                        if (r != null && r.getNumbers() != null) {
                            log.info("Successfully received response with {} numbers", r.getNumbers().size());
                        } else {
                            log.warn("Received null or empty response");
                        }
                    })
                    .onErrorResume(e -> {
                        log.error("Error fetching numbers from {}: {}", fullUrl, e.getMessage());
                        e.printStackTrace();
                        return Mono.empty();
                    })
                    .block();
            
            if (response == null) {
                log.warn("Response is null after API call");
                return new NumbersResponse(Collections.emptyList());
            }
            
            return response;
        } catch (Exception e) {
            log.error("Exception while fetching numbers from {}: {}", fullUrl, e.getMessage());
            e.printStackTrace();
            return new NumbersResponse(Collections.emptyList());
        }
    }

    private void addUniqueNumbersToWindow(List<Integer> newNumbers) {
        if (newNumbers == null || newNumbers.isEmpty()) {
            return;
        }

        for (Integer number : newNumbers) {
            if (!numberWindow.contains(number)) {
                if (numberWindow.size() >= windowSize) {
                    // Remove the oldest number
                    numberWindow.removeFirst();
                }
                numberWindow.addLast(number);
            }
        }
    }

    private double calculateAverage(List<Integer> numbers) {
        if (numbers == null || numbers.isEmpty()) {
            return 0.0;
        }
        
        return numbers.stream()
                .mapToDouble(Integer::doubleValue)
                .average()
                .orElse(0.0);
    }
} 