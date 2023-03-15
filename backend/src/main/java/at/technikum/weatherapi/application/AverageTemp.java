package at.technikum.weatherapi.application;

import lombok.*;

import java.util.List;

@Data
public class AverageTemp {
  private String country;
  private List<Double> temps;
  private Double average;
}
