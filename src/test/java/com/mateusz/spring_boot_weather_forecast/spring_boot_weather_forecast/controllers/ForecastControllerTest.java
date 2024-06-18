package com.mateusz.spring_boot_weather_forecast.spring_boot_weather_forecast.controllers;

import com.mateusz.spring_boot_weather_forecast.spring_boot_weather_forecast.DTO.ForecastResponse;
import com.mateusz.spring_boot_weather_forecast.spring_boot_weather_forecast.DTO.ForecastResponse.Day;
import com.mateusz.spring_boot_weather_forecast.spring_boot_weather_forecast.DTO.ForecastResponse.Forecast;
import com.mateusz.spring_boot_weather_forecast.spring_boot_weather_forecast.DTO.ForecastResponse.ForecastDay;
import com.mateusz.spring_boot_weather_forecast.spring_boot_weather_forecast.services.ForecastService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import reactor.core.publisher.Mono;

@WebFluxTest(controllers = ForecastController.class)
public class ForecastControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private ForecastService forecastService;

    private ForecastResponse mockResponse;

    @BeforeEach
    public void setup() {
        Day day = new Day(
                BigDecimal.valueOf(20.0), // maxtemp_c
                BigDecimal.valueOf(10.0), // mintemp_c
                BigDecimal.valueOf(15.0), // avgtemp_c
                BigDecimal.valueOf(10.0), // maxwind_kph
                BigDecimal.valueOf(0.0), // totalprecip_mm
                BigDecimal.valueOf(0.0), // totalsnow_cm
                BigDecimal.valueOf(70.0), // avghumidity
                BigDecimal.valueOf(10.0), // avgvis_km
                BigDecimal.valueOf(5.0) // uv
        );
        ForecastDay forecastDay = new ForecastDay(LocalDate.now(), day);
        Forecast forecast = new Forecast(List.of(forecastDay));
        mockResponse = new ForecastResponse(forecast);
    }

    @ParameterizedTest
    @ValueSource(strings = { "/v1/forecasts/Warsaw", "/v1/forecasts/Lodz", "/v1/forecasts/Krakow",
            "/v1/forecasts/Wroclaw", "/v1/forecasts/Poznan" })
    void testCitySpecificEndpoints(String uri) {
        Mockito.when(forecastService.getForecastForCity(Mockito.anyString())).thenReturn(Mono.just(mockResponse)); // Stub

        webTestClient.get().uri(uri)
                .accept(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML) 
                .exchange()
                .expectStatus().isOk() // Assertion
                .expectBody(ForecastResponse.class).isEqualTo(mockResponse); // Assertion
    }

    @ParameterizedTest
    @ValueSource(strings = { "/v1/forecasts" })
    void testGetForecastsForFiveBiggestPolishCities(String uri) {
        Mockito.when(forecastService.getForecastForCity(Mockito.anyString())).thenReturn(Mono.just(mockResponse)); // Stub

        webTestClient.get().uri(uri)
                .accept(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML) 
                .exchange()
                .expectStatus().isOk() // Assertion
                .expectBodyList(ForecastResponse.class).hasSize(5); // Assertion
    }
}