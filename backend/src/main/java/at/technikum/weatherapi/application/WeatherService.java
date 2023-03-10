package at.technikum.weatherapi.application;


import at.technikum.weatherapi.infrastructure.adapter.WeatherApiAdapter;
import at.technikum.weatherapi.infrastructure.adapter.model.WeatherApiDto;
import lombok.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WeatherService {

  private final WeatherApiAdapter weatherApiAdapter;

  public WeatherApiDto getWeatherData() {
    return weatherApiAdapter.getWeatherData();
  }
}
