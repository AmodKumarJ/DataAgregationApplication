package com.amod.demo.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.concurrent.CompletableFuture;

@Service
@Async
public class StockApi {
    @Autowired
    private RestTemplate restTemplate;

    @Value("${stock.api.key}")
    private String apiKey;



    private static final String STOCK_API_URI = "https://api.marketaux.com/v1/news/all";

    public CompletableFuture<String> getStockData(String symbols) {
        simulateDelay(4000);

        String url = UriComponentsBuilder.fromHttpUrl(STOCK_API_URI)
                .queryParam("symbols", symbols)
                .queryParam("filter_entities", true)
                .queryParam("api_token", apiKey)
                .toUriString();

        String response = restTemplate.getForObject(url, String.class);
        return CompletableFuture.completedFuture("Stock Market Data: " + response);
    }
    public void simulateDelay(int ms) {
        try {
            Thread.sleep(ms); // Simulates delay
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore interrupted status
        }
    }
}
