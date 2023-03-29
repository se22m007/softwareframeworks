package at.technikum.weatherapi.infrastructure.adapter.model;

import com.influxdb.annotations.Column;
import com.influxdb.annotations.Measurement;
import lombok.*;

import java.time.Instant;
import java.time.LocalDateTime;
@Measurement(name = "currentdto")
@Data
public class CurrentDto {
  @Column(tag = true)
  private String locationName;
  @Column
  private Double temp_c;
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
