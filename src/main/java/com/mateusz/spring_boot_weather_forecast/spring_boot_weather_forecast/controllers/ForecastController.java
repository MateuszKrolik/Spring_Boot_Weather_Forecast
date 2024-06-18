package com.mateusz.spring_boot_weather_forecast.spring_boot_weather_forecast.controllers;

import com.mateusz.spring_boot_weather_forecast.spring_boot_weather_forecast.DTO.ForecastResponse;
import com.mateusz.spring_boot_weather_forecast.spring_boot_weather_forecast.services.ForecastService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1")
public class ForecastController {

    private final ForecastService forecastService;

    // constructor dependency injection
    public ForecastController(ForecastService forecastService) {
        this.forecastService = forecastService;
    }

    @GetMapping("/forecasts/{city}")
    public Mono<ForecastResponse> getForecastForSpecificCity(@PathVariable String city) {
        return forecastService.getForecastForCity(city).onErrorResume(
                e -> Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid city name provided.")));
    }

    @GetMapping("/forecasts")
    public Flux<ForecastResponse> getForecastsForFiveBiggestPolishCities() {
        return Flux.just("Warsaw", "Lodz", "Krakow", "Wroclaw", "Poznan")
                .flatMap(city -> forecastService.getForecastForCity(city));
    }

    @GetMapping("/forecasts/Warsaw")
    public Mono<ForecastResponse> getForecastForWarsaw() {
        return forecastService.getForecastForCity("Warsaw");
    }

    @GetMapping("/forecasts/Lodz")
    public Mono<ForecastResponse> getForecastForLodz() {
        return forecastService.getForecastForCity("Lodz");
    }

    @GetMapping("/forecasts/Krakow")
    public Mono<ForecastResponse> getForecastForKrakow() {
        return forecastService.getForecastForCity("Krakow");
    }

    @GetMapping("/forecasts/Wroclaw")
    public Mono<ForecastResponse> getForecastForWroclaw() {
        return forecastService.getForecastForCity("Wroclaw");
    }

    @GetMapping("/forecasts/Poznan")
    public Mono<ForecastResponse> getForecastForPoznan() {
        return forecastService.getForecastForCity("Poznan");
    }

}