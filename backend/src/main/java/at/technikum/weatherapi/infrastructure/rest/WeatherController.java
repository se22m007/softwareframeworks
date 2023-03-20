package at.technikum.weatherapi.infrastructure.rest;

import at.technikum.weatherapi.WeatherApiCompactDto;
import at.technikum.weatherapi.application.WeatherService;
import at.technikum.weatherapi.domain.model.WeatherApiResponseDto;
import lombok.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/weather")
public class WeatherController {

  private final WeatherService weatherService;

  /**
   * For publishing new data via avro schema.
   */
  @CrossOrigin
  @GetMapping
  public void publishWeatherData() {
    weatherService.publishCompactWeatherData();
  }

  /**
   * For publishing new data as jsons for the aggregate exercise
   */
  @CrossOrigin
  @GetMapping("/json")
  public void publishWeatherDataJson() {
    weatherService.publishCompactWeatherDataJson();
  }

  /**
   * For the exercise of creating, producing and consuming data via avro schema
   */
  @CrossOrigin
  @GetMapping("/consume")
  public List<WeatherApiResponseDto> consumeWeatherData() {
    final List<WeatherApiCompactDto> dtos = weatherService.consumeCompactWeatherData();
    return dtos.stream()
        .map(dto -> {
          final WeatherApiResponseDto responseElement = new WeatherApiResponseDto();
          responseElement.setCountry(dto.getCountry());
          responseElement.setTemp(dto.getTemp());
          responseElement.setLocationName(dto.getLocationName());
          responseElement.setTemp(dto.getFeelslikeTemp());
          return responseElement;
        })
        .toList();
  }

  /**
   * For testing purposes to see if the json consuming works
   */
  @CrossOrigin
  @GetMapping("/consume/json")
  public List<String> consumeWeatherDataJson() {
    return weatherService.consumeWeatherDataJson();
  }

  /**
   * For the exercise of aggregating a stream
   */
  @CrossOrigin
  @GetMapping("/aggregate")
  public void aggregateWeatherData() {
    weatherService.aggregateWeatherData();
  }
}
