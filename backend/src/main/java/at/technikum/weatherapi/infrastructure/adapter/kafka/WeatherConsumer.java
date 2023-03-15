package at.technikum.weatherapi.infrastructure.adapter.kafka;

import at.technikum.weatherapi.WeatherApiCompactDto;
import at.technikum.weatherapi.infrastructure.config.KafkaConfig;
import lombok.*;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Component
@RequiredArgsConstructor
public class WeatherConsumer {

  private final KafkaConfig kafkaConfig;

  public List<WeatherApiCompactDto> consume() {
    final Properties properties = kafkaConfig.loadConsumerConfig();
    KafkaConsumer<String, WeatherApiCompactDto> consumer = new KafkaConsumer<>(properties);
    consumer.subscribe(List.of(KafkaConfig.WEATHER_TOPIC));
    List<WeatherApiCompactDto> result = new ArrayList<>();
    while (CollectionUtils.isEmpty(result)){
      ConsumerRecords<String, WeatherApiCompactDto> records = consumer.poll(Duration.ofMillis(100));

      for (ConsumerRecord<String, WeatherApiCompactDto> record : records){
        if(record != null) {
          result.add(record.value());
        }
      }
    }
    return result;
  }
}
