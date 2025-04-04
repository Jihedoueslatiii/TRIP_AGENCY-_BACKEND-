package com.esprit.tripagency.vol_management;

import com.esprit.tripagency.vol_management.WeatherService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/{city}")
    public String getWeather(@PathVariable String city) {
        return weatherService.getWeather(city);
    }
}
