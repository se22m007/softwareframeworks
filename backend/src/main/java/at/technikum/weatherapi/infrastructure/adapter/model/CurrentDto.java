package at.technikum.weatherapi.infrastructure.adapter.model;

import com.influxdb.annotations.Column;
import com.influxdb.annotations.Measurement;
import lombok.*;

import java.time.LocalDateTime;
@Data
@Measurement(name = "currentdto")
public class CurrentDto {
  @Column(tag = true, timestamp = true)
  private LocalDateTime lastUpdated;
  @Column
  private Double temp_c;
  @Column
  private ConditionDto condition;
  @Column
  private Double wind_kph;
  @Column
  private Integer humidity;
  @Column
  private Integer cloud;
  @Column
  private Double feelslike_c;
  @Column
  private Double uv;
}
