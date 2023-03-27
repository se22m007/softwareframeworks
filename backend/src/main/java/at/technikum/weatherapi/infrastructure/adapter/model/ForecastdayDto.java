package at.technikum.weatherapi.infrastructure.adapter.model;

import com.influxdb.annotations.Column;
import lombok.*;

import java.time.LocalDate;
@Data
public class ForecastdayDto {
  @Column
  private LocalDate date;
  @Column
  private DayDto day;
  @Column
  private AstroDto astro;
}
