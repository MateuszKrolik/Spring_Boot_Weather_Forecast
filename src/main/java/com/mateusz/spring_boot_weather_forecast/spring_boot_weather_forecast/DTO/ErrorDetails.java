package com.mateusz.spring_boot_weather_forecast.spring_boot_weather_forecast.DTO;

import java.time.LocalDateTime;

public record ErrorDetails(LocalDateTime timeStamp, String message, String details) {
}