package com.mateusz.spring_boot_weather_forecast.spring_boot_weather_forecast.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record ForecastResponse(Forecast forecast) {

    public record Forecast(List<ForecastDay> forecastday) {
    }

    public record ForecastDay(LocalDate date, Day day) {
    }

    public record Day(
            BigDecimal maxtemp_c,
            BigDecimal mintemp_c,
            BigDecimal avgtemp_c,
            BigDecimal maxwind_kph,
            BigDecimal totalprecip_mm,
            BigDecimal totalsnow_cm,
            BigDecimal avghumidity,
            BigDecimal avgvis_km,
            BigDecimal uv) {
    }
}