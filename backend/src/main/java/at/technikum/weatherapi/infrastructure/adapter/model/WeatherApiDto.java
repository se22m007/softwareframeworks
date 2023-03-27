package at.technikum.weatherapi.infrastructure.adapter.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.influxdb.annotations.Column;
import com.influxdb.annotations.Measurement;
import lombok.*;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Measurement(name = "weatherapidto")
public class WeatherApiDto {
    @Column(name = "location")
    private LocationDto location;
    @Column(name = "current")
    private CurrentDto current;
    @Column(name = "forecast")
    private ForecastDto forecast;
}
