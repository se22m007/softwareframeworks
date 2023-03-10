package at.technikum.weatherapi.infrastructure.rest;

import at.technikum.weatherapi.infrastructure.adapter.WeatherApiAdapter;
import at.technikum.weatherapi.infrastructure.adapter.model.WeatherApiDto;
import lombok.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/weather")
public class WeatherController {

  private final WeatherPublisher publisher;

  @CrossOrigin
  @GetMapping
  public void publishWeatherData() {
    publisher.sendMessage();
  }
}
