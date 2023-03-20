package at.technikum.weatherapi.application.model;

import lombok.*;

@Data
public class WeatherApiJsonDto {
  private String locationName;
  private Double temp;
  private Double feelslikeTemp;
  private String country;
}
