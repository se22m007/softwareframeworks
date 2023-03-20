package at.technikum.weatherapi.domain.model;

import lombok.*;

/**
 * The two dtos are basicly the same but one is intended to be used as response on our endpoints and this
 * one is used as response from our kafka.
 */
@Data
public class WeatherApiJsonDto {
  private String locationName;
  private Double temp;
  private Double feelslikeTemp;
  private String country;
}
