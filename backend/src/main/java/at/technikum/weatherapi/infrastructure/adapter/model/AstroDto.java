package at.technikum.weatherapi.infrastructure.adapter.model;

import lombok.*;

import java.time.LocalDateTime;

@Data
public class AstroDto {
  private String sunrise;
  private String sunset;
  private String moonrise;
  private String moonset;
  private String moon_phase;
  private String moon_illumination;
}
