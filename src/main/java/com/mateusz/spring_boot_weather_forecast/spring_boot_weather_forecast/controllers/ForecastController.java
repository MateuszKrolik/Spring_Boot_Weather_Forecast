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

    @GetMapping(value = "/forecasts/{city}", produces = { "application/json",
    "application/xml" })
    public Mono<ForecastResponse> getForecastForSpecificCity(@PathVariable String city) {
        return forecastService.getForecastForCity(city).onErrorResume(
                e -> Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid city name provided.")));
    }

    @GetMapping(value = "/forecasts", produces = { "application/json",
    "application/xml" })
    public Flux<ForecastResponse> getForecastsForFiveBiggestPolishCities() {
        return Flux.just("Warsaw", "Lodz", "Krakow", "Wroclaw", "Poznan")
                .flatMap(city -> forecastService.getForecastForCity(city));
    }

    @GetMapping(value = "/forecasts/Warsaw", produces = { "application/json",
    "application/xml" })
    public Mono<ForecastResponse> getForecastForWarsaw() {
        return forecastService.getForecastForCity("Warsaw");
    }

    @GetMapping(value = "/forecasts/Lodz", produces = { "application/json",
    "application/xml" })
    public Mono<ForecastResponse> getForecastForLodz() {
        return forecastService.getForecastForCity("Lodz");
    }

    @GetMapping(value = "/forecasts/Krakow", produces = { "application/json",
    "application/xml" })
    public Mono<ForecastResponse> getForecastForKrakow() {
        return forecastService.getForecastForCity("Krakow");
    }

    @GetMapping(value = "/forecasts/Wroclaw", produces = { "application/json",
    "application/xml" })
    public Mono<ForecastResponse> getForecastForWroclaw() {
        return forecastService.getForecastForCity("Wroclaw");
    }

    @GetMapping(value = "/forecasts/Poznan", produces = { "application/json",
    "application/xml" })
    public Mono<ForecastResponse> getForecastForPoznan() {
        return forecastService.getForecastForCity("Poznan");
    }

}