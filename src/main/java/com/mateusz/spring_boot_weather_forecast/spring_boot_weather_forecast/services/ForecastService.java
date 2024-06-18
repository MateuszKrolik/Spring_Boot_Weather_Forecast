package com.mateusz.spring_boot_weather_forecast.spring_boot_weather_forecast.services;

import com.mateusz.spring_boot_weather_forecast.spring_boot_weather_forecast.DTO.ForecastResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ForecastService {

    private final WebClient webClient;
    private final String apiKey;

    public ForecastService(WebClient.Builder webClientBuilder, @Value("${weather.api.key}") String apiKey) {
        this.apiKey = apiKey;
        this.webClient = webClientBuilder.baseUrl("http://api.weatherapi.com/v1").build();
    }

    public Mono<ForecastResponse> getForecastForCity(String city) {
        return this.webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/forecast.json")
                        .queryParam("key", apiKey)
                        .queryParam("q", city)
                        .queryParam("days", 3)
                        .queryParam("aqi", "no")
                        .queryParam("alerts", "no")
                        .build())
                .retrieve()
                .bodyToMono(ForecastResponse.class);
    }
}