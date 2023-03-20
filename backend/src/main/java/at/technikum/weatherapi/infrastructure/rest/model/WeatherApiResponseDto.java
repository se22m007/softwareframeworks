package at.technikum.weatherapi.infrastructure.rest.model;

import lombok.*;

@Data
public class WeatherApiResponseDto {
  private String locationName;
  private Double temp;
  private Double feelslikeTemp;
  private String country;
}