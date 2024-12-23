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
public class NewsApi {
    @Autowired
    RestTemplate restTemplate;
    @Value("${news.api.key}")
    private String api_key;



    private static final String NEWS_API_URI = "https://newsapi.org/v2/top-headlines";

    public CompletableFuture<String> getNewsData(String country, String category) {
        simulateDelay(3000);

        String url = UriComponentsBuilder.fromHttpUrl(NEWS_API_URI)
                .queryParam("country", country)
                .queryParam("category", category)
                .queryParam("apiKey", api_key)
                .toUriString();

        String response = restTemplate.getForObject(url, String.class);
        return CompletableFuture.completedFuture("News Data: " + response);
    }
    public void simulateDelay(int ms) {
        try {
            Thread.sleep(ms); // Simulates delay
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore interrupted status
        }
    }
}
