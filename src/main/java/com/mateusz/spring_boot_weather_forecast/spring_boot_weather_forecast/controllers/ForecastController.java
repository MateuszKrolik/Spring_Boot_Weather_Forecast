package com.mateusz.spring_boot_weather_forecast.spring_boot_weather_forecast.controllers;

import com.mateusz.spring_boot_weather_forecast.spring_boot_weather_forecast.DTO.ForecastResponse;
import com.mateusz.spring_boot_weather_forecast.spring_boot_weather_forecast.exception.CityNotFoundException;
import com.mateusz.spring_boot_weather_forecast.spring_boot_weather_forecast.services.ForecastServiceImpl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1")
public class ForecastController {

    private final ForecastServiceImpl forecastService;

    public ForecastController(ForecastServiceImpl forecastService) {
        this.forecastService = forecastService;
    }

    @GetMapping(value = "/forecasts/{city}", produces = { "application/json", "application/xml" })
    public Mono<ResponseEntity<Object>> getForecastForSpecificCity(@PathVariable String city) {
        return forecastService.getForecastForCity(city)
                .map(forecast -> ResponseEntity.ok((Object) forecast))
                .onErrorResume(WebClientResponseException.class, e -> {
                    if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                        throw new CityNotFoundException("City not found: " + city);
                    } else {
                        throw new RuntimeException("An unexpected error occurred.");
                    }
                });
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