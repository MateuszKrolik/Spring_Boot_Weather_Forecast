package com.mateusz.spring_boot_weather_forecast.spring_boot_weather_forecast.services;

import com.mateusz.spring_boot_weather_forecast.spring_boot_weather_forecast.DTO.ForecastResponse;
import reactor.core.publisher.Mono;

public interface ForecastService {
    Mono<ForecastResponse> getForecastForCity(String city);
}