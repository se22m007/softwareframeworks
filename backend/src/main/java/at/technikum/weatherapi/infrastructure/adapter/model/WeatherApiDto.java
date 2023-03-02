package at.technikum.weatherapi.infrastructure.adapter.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherApiDto {
  private LocationDto location;
  private CurrentDto current;
  private ForecastDto forecast;
}
