package at.technikum.weatherapi.infrastructure.adapter.model;

import com.influxdb.annotations.Column;
import lombok.*;

import java.util.List;
@Data
public class ForecastDto {
  @Column
  private List<ForecastdayDto> forecastday;
}
