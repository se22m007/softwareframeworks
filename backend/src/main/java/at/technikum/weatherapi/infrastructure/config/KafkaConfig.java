package at.technikum.weatherapi.infrastructure.config;

import io.confluent.connect.avro.AvroConverter;
import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.tomcat.Jar;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

@Configuration
public class KafkaConfig {

  public static final String WEATHER_TOPIC = "weather-stats";
  public static final String WEATHER_TOPIC_JSON = "weather-stats-json-new";
  public static final String WEATHER_AGGREGATED_TOPIC = "weather-aggregated-stats-without-avro";
  public static final String WEATHER_AGGREGATED_RESULT_TOPIC = "weather-result-without-avro";
  public static final String WEATHER_KEY = "weather_key";

  private final String configFilePath;

  public KafkaConfig(@Value("${spring.kafka.config-file}") final String configFilePath) {
    this.configFilePath = configFilePath;
  }

  public Properties loadConfig() {
    if (!Files.exists(Paths.get(configFilePath))) {
      throw new IllegalArgumentException("Isn Problem.");
    }
    final Properties cfg = new Properties();
    try (final InputStream inputStream = new FileInputStream(configFilePath)) {
      cfg.load(inputStream);
    } catch (final IOException e) {
      e.printStackTrace();
    }
    cfg.put("application.id", "test");
    return cfg;
  }

  public Properties loadProducerConfig() {
    final Properties properties = loadConfig();
    properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
        StringSerializer.class);
    properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
        KafkaAvroSerializer.class);
    return properties;
  }

  public Properties loadProducerJsonConfig() {
    final Properties properties = loadConfig();
    properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
        StringSerializer.class);
    properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
        JsonSerializer.class);
    return properties;
  }

  public Properties loadConsumerConfig() {
    final Properties properties = loadConfig();
    properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
        StringDeserializer.class);
    properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
        KafkaAvroDeserializer.class);
    properties.put("specific.avro.reader", true);
    properties.put(ConsumerConfig.GROUP_ID_CONFIG, "kafka-java-getting-started");
    properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
    return properties;
  }

  public Properties loadConsumerJsonConfig() {
    final Properties properties = loadConfig();
    properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
        StringDeserializer.class);
    properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
        JsonDeserializer.class);
    properties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
    properties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, AverageTempSerde.class);
    properties.put("specific.avro.reader", true);
    properties.put(ConsumerConfig.GROUP_ID_CONFIG, "kafka-java-getting-started");
    properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
    return properties;
  }
}