package com.amod.demo.Controller;

import com.amod.demo.Services.NewsApi;
import com.amod.demo.Services.StockApi;
import com.amod.demo.Services.WeatherApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/data")
public class DataAggregatorController {

    @Autowired
    private WeatherApi weatherApi;
    @Autowired
    private NewsApi newsApi;
    @Autowired
    private StockApi stockApi;


    @GetMapping("/aggregate")
    public ResponseEntity<Map<String, String>> getAggregatedData() {
        CompletableFuture<String> weather = weatherApi.getWeatherData("London");
        CompletableFuture<String> news = newsApi.getNewsData("us","business");
        CompletableFuture<String> stocks = stockApi.getStockData("TSLA");

        // Wait for all async tasks to complete
        CompletableFuture.allOf(weather, news, stocks).join();

        // Combine results
        Map<String, String> result = new HashMap<>();
        try {
            result.put("weather", weather.get());
            result.put("news", news.get());
            result.put("stocks", stocks.get());
        } catch (Exception e) {
            result.put("error", "Failed to fetch all data: " + e.getMessage());
        }

        return ResponseEntity.ok(result);
    }
}
