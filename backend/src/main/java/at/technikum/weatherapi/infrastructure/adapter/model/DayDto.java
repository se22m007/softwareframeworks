package at.technikum.weatherapi.infrastructure.adapter.model;

import lombok.*;

@Data
public class DayDto {
  private Double maxtemp_c;
  private Double mintemp_c;
  private Double avgtemp_c;
  private Double maxwind_kph;
  private Double totalprecip_mm;
  private Double totalsnow_cm;
  private Integer daily_will_it_rain;
  private Integer daily_chance_of_rain;
  private Integer daily_will_it_snow;
  private Integer daily_chance_of_snow;
  private double uv;
}
