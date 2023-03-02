package at.technikum.weatherapi.infrastructure.adapter.model;

import lombok.*;

import java.time.LocalDate;
@Data
public class ForecastdayDto {
  private LocalDate date;
  private DayDto day;
  private AstroDto astro;
}
