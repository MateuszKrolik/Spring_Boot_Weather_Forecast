package com.mateusz.spring_boot_weather_forecast.spring_boot_weather_forecast.services;

import com.mateusz.spring_boot_weather_forecast.spring_boot_weather_forecast.DTO.ForecastResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ForecastServiceImpl implements ForecastService {

    private final WebClient webClient;
    private final String apiKey;
    private final String baseUrl;
    private final String path;

    public ForecastServiceImpl(WebClient.Builder webClientBuilder,
            @Value("${WEATHER_API_KEY}") String apiKey,
            @Value("${WEATHER_API_BASE_URL}") String baseUrl,
            @Value("${WEATHER_API_PATH}") String path) {
        this.apiKey = apiKey;
        this.baseUrl = baseUrl;
        this.path = path;
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
    }

    @Override
    public Mono<ForecastResponse> getForecastForCity(String city) {
        return this.webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(path)
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