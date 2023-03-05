package at.technikum.weatherapi.infrastructure.rest;

import at.technikum.weatherapi.infrastructure.adapter.WeatherApiAdapter;
import at.technikum.weatherapi.infrastructure.adapter.model.WeatherApiDto;
import at.technikum.weatherapi.infrastructure.config.JsonMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class WeatherPublisher {

   private final KafkaTemplate<String, String> kafkaTemplate;

   private final WeatherApiAdapter weatherApiAdapter;

  public WeatherPublisher(
      final KafkaTemplate<String, String> kafkaTemplate,
      final WeatherApiAdapter weatherApiAdapter) {
    this.kafkaTemplate = kafkaTemplate;
    this.weatherApiAdapter = weatherApiAdapter;
  }

  public void sendMessage() {
      kafkaTemplate.send("weather", JsonMapper.deserialize(weatherApiAdapter.getWeatherData()));
  }
}
