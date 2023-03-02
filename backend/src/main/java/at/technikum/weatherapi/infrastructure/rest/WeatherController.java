package at.technikum.weatherapi.infrastructure.rest;

import at.technikum.weatherapi.infrastructure.adapter.WeatherApiAdapter;
import lombok.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/weather")
public class WeatherController {

  private final WeatherApiAdapter adapter;

  @GetMapping
  public void getWeatherData() {
    adapter.getWeatherData();
  }
}
