package at.technikum.weatherapi.infrastructure.adapter.model;

import lombok.*;

import java.time.LocalDateTime;
@Data
public class CurrentDto {
  private LocalDateTime lastUpdated;
  private Double temp_c;
  private ConditionDto condition;
  private Double wind_kph;
  private Integer humidity;
  private Integer cloud;
  private Double feelslike_c;
  private Double uv;
}
