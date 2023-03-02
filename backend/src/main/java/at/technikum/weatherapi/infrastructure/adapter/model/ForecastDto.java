package at.technikum.weatherapi.infrastructure.adapter.model;

import lombok.*;

import java.util.List;
@Data
public class ForecastDto {
  private List<ForecastdayDto> forecastday;
}
