package at.technikum.weatherapi.application;


import at.technikum.weatherapi.WeatherApiCompactDto;
import at.technikum.weatherapi.application.model.WeatherApiJsonDto;
import at.technikum.weatherapi.infrastructure.adapter.WeatherApiAdapter;
import at.technikum.weatherapi.infrastructure.adapter.kafka.WeatherConsumer;
import at.technikum.weatherapi.infrastructure.adapter.kafka.WeatherProducer;
import at.technikum.weatherapi.infrastructure.adapter.model.WeatherApiDto;
import at.technikum.weatherapi.infrastructure.config.AverageTempSerde;
import at.technikum.weatherapi.infrastructure.config.CustomSerdes;
import at.technikum.weatherapi.infrastructure.config.KafkaConfig;

import at.technikum.weatherapi.infrastructure.rest.model.WeatherApiResponseDto;
import lombok.*;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.state.KeyValueStore;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;

@Service
@RequiredArgsConstructor
public class WeatherService {
  private static final List<String> cities = List.of(
      "Vienna", "Graz", "Salzburg", "Linz", "Berlin", "Frankfurt", "München", "Heidelberg"
  );

  private final WeatherApiAdapter weatherApiAdapter;
  private final WeatherProducer weatherProducer;
  private final WeatherConsumer weatherConsumer;
  private final KafkaConfig kafkaConfig;

  public void publishCompactWeatherData() {
    for (final String city : cities) {
      final WeatherApiDto weatherApiDto = weatherApiAdapter.getWeatherData(city);
      final WeatherApiCompactDto weatherApiCompactDto = WeatherApiCompactDto.newBuilder()
          .setLocationName(weatherApiDto.getLocation().getName())
          .setFeelslikeTemp(weatherApiDto.getCurrent().getFeelslike_c())
          .setTemp(weatherApiDto.getCurrent().getTemp_c())
          .setCountry(weatherApiDto.getLocation().getCountry())
          .build();
      weatherProducer.sendRecord(KafkaConfig.WEATHER_TOPIC, KafkaConfig.WEATHER_KEY,
          weatherApiCompactDto);
    }
  }

  public void publishCompactWeatherDataJson() {
    for (final String city : cities) {
      final WeatherApiDto weatherApiDto = weatherApiAdapter.getWeatherData(city);
      final WeatherApiJsonDto weatherApiJsonDto = new WeatherApiJsonDto();
          weatherApiJsonDto.setLocationName(weatherApiDto.getLocation().getName());
          weatherApiJsonDto.setFeelslikeTemp(weatherApiDto.getCurrent().getFeelslike_c());
          weatherApiJsonDto.setTemp(weatherApiDto.getCurrent().getTemp_c());
          weatherApiJsonDto.setCountry(weatherApiDto.getLocation().getCountry());
      weatherProducer.sendRecordJson(KafkaConfig.WEATHER_TOPIC_JSON, KafkaConfig.WEATHER_KEY,
          weatherApiJsonDto);
    }
  }

  public List<WeatherApiCompactDto> consumeCompactWeatherData() {
    return weatherConsumer.consume();
  }

  public void aggregateWeatherData() {
    final Properties properties = kafkaConfig.loadConsumerJsonConfig();
    final StreamsBuilder streamsBuilder = new StreamsBuilder();

    streamsBuilder.<String, WeatherApiJsonDto>stream(KafkaConfig.WEATHER_TOPIC_JSON)
        .groupBy((key, value) -> value.getCountry())
        .aggregate(WeatherService::getAverageTemp, (key, value, aggregator) -> {
          aggregator.getTemps().add(value.getTemp());
          setNewAverage(aggregator);
          return aggregator;
        }, Materialized.<String, AverageTemp, KeyValueStore<Bytes, byte[]>>as(KafkaConfig.WEATHER_AGGREGATED_TOPIC)
            .withValueSerde(new AverageTempSerde()))
        .toStream()
        .to(KafkaConfig.WEATHER_AGGREGATED_RESULT_TOPIC, Produced.with(Serdes.String(),
            new AverageTempSerde()));

    final Topology topology = streamsBuilder.build();
    final KafkaStreams kafkaStreams = new KafkaStreams(topology, properties);

    kafkaStreams.start();


  }
  private static AverageTemp getAverageTemp() {
    final var temp = new AverageTemp();
    temp.setTemps(new ArrayList<>());
    return temp;
  }

  private void setNewAverage(AverageTemp aggregator) {
    final Double sum = aggregator.getTemps().stream()
        .mapToDouble(p -> p)
        .sum();
    aggregator.setAverage(sum / (double) aggregator.getTemps().size());
  }
}
