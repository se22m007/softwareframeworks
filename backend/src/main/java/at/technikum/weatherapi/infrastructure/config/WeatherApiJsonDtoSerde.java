package at.technikum.weatherapi.infrastructure.config;

import at.technikum.weatherapi.application.AverageTemp;
import at.technikum.weatherapi.application.model.WeatherApiJsonDto;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

public class WeatherApiJsonDtoSerde implements Serde<WeatherApiJsonDto> {
  public WeatherApiJsonDtoSerde() {

  }

  @Override
  public Serializer<WeatherApiJsonDto> serializer() {
    return new JsonSerializer<>();
  }

  @Override
  public Deserializer<WeatherApiJsonDto> deserializer() {
    return new JsonDeserializer<>(WeatherApiJsonDto.class);
  }


}
