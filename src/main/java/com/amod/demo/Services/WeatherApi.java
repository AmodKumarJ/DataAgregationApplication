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
public class WeatherApi {

    @Autowired
    RestTemplate restTemplate;

    @Value("${weather.api.key}")
    private String api_key;



    private static final String WEATHER_URI = "https://api.openweathermap.org/data/2.5/weather" ;

    public CompletableFuture<String> getWeatherData(String city) {
        simulateDelay(2000);
        String url = UriComponentsBuilder.fromHttpUrl(WEATHER_URI)
                .queryParam("q", city)
                .queryParam("appid", api_key)
                .queryParam("units", "metric")
                .toUriString();

        String response =  restTemplate.getForObject(url,String.class);
        return CompletableFuture.completedFuture("weather Date"+response);

    }
    public void simulateDelay(int ms) {
        try {
            Thread.sleep(ms); // Simulates delay
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore interrupted status
        }
    }
}
