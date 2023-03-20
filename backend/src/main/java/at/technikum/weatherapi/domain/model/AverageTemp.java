package at.technikum.weatherapi.domain.model;

import lombok.*;

import java.util.List;

@Data
public class AverageTemp {
  private String country;
  private List<Double> temps;
  private Double average;


}
