package com.mateusz.spring_boot_weather_forecast.spring_boot_weather_forecast;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class SpringBootWeatherForecastApplication {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.load();
		dotenv.entries().forEach(e -> System.setProperty(e.getKey(), e.getValue()));


		SpringApplication.run(SpringBootWeatherForecastApplication.class, args);
	}

}
