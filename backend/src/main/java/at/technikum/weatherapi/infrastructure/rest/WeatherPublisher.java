package at.technikum.weatherapi.infrastructure.rest;

import at.technikum.weatherapi.application.WeatherService;
import at.technikum.weatherapi.infrastructure.config.JsonMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class WeatherPublisher {

   private final KafkaTemplate<String, String> kafkaTemplate;

   private final WeatherService weatherService;

  public WeatherPublisher(
      final KafkaTemplate<String, String> kafkaTemplate,
      final WeatherService weatherService) {
    this.kafkaTemplate = kafkaTemplate;
    this.weatherService = weatherService;
  }

  public void sendMessage() {
      kafkaTemplate.send("weather", JsonMapper.deserialize(weatherService.getWeatherData()));
  }
}
