package at.technikum.weatherapi.infrastructure.adapter.kafka;

import at.technikum.weatherapi.infrastructure.config.KafkaConfig;
import lombok.*;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WeatherProducer {

  private final KafkaConfig kafkaConfig;

  public void sendRecord(final String topic, final String key, final Object value) {
    final Producer<String, Object> producer =
        new KafkaProducer<>(kafkaConfig.loadProducerConfig());
    producer.send(new ProducerRecord<>(topic, key, value));
    producer.close();
  }

  public void sendRecordJson(final String topic, final String key, final Object value) {
    final Producer<String, Object> producer =
        new KafkaProducer<>(kafkaConfig.loadProducerJsonConfig());
    producer.send(new ProducerRecord<>(topic, key, value));
    producer.close();
  }
}
