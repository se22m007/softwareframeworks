package at.technikum.weatherapi.infrastructure.adapter.model;

import jakarta.annotation.sql.DataSourceDefinitions;
import lombok.*;

@Data
public class LocationDto {
  private String name;
  private String country;
}
