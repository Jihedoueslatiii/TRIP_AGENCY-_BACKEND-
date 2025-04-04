package com.esprit.tripagency.vol_management;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    @Value("${weather.api.key}")
    private String apiKey;

    @Value("${weather.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public String getWeather(String city) {
        String url = apiUrl + "?q=" + city + "&appid=" + apiKey + "&units=metric";
        return restTemplate.getForObject(url, String.class);
    }
}
