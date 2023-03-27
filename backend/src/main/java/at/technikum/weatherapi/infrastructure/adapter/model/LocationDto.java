package at.technikum.weatherapi.infrastructure.adapter.model;

import com.influxdb.annotations.Column;
import jakarta.annotation.sql.DataSourceDefinitions;
import lombok.*;

@Data
public class LocationDto {
  @Column
  private String name;
  @Column
  private String country;
}
