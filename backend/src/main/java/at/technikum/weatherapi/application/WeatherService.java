package at.technikum.weatherapi.application;


import at.technikum.weatherapi.WeatherApiCompactDto;
import at.technikum.weatherapi.domain.model.AverageTemp;
import at.technikum.weatherapi.domain.model.WeatherApiJsonDto;
import at.technikum.weatherapi.domain.repository.WeatherRepository;
import at.technikum.weatherapi.infrastructure.adapter.WeatherApiAdapter;
import at.technikum.weatherapi.infrastructure.adapter.kafka.WeatherConsumer;
import at.technikum.weatherapi.infrastructure.adapter.kafka.WeatherProducer;
import at.technikum.weatherapi.infrastructure.adapter.model.CurrentDto;
import at.technikum.weatherapi.infrastructure.adapter.model.WeatherApiDto;
import at.technikum.weatherapi.infrastructure.config.kafka.KafkaConfig;
import at.technikum.weatherapi.infrastructure.config.kafka.serdes.AverageTempSerde;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
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

/**
 * Lots of duplicated code for the purpose of convenience.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class WeatherService {
  private static final List<String> cities = List.of(
      "Vienna", "Graz", "Salzburg", "Linz", "Berlin", "Frankfurt", "Muenchen", "Heidelberg"
  );

  private final WeatherApiAdapter weatherApiAdapter;
  private final WeatherProducer weatherProducer;
  private final WeatherConsumer weatherConsumer;
  private final KafkaConfig kafkaConfig;

  private final WeatherRepository weatherRepository;

  public void publishCompactWeatherData() {
    for (final String city : cities) {
      final WeatherApiDto weatherApiDto = weatherApiAdapter.getWeatherData(city);
      final WeatherApiCompactDto weatherApiCompactDto = WeatherApiCompactDto.newBuilder()
          .setLocationName(weatherApiDto.getLocation().getName())
          .setFeelslikeTemp(weatherApiDto.getCurrent().getFeelslike_c())
          .setTemp(weatherApiDto.getCurrent().getTemp_c())
          .setCountry(weatherApiDto.getLocation().getCountry())
          .build();
      weatherRepository.save(weatherApiDto.getCurrent());
      /*
      weatherProducer.sendRecord(KafkaConfig.WEATHER_TOPIC, KafkaConfig.WEATHER_KEY,
          weatherApiCompactDto);

       */
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
      weatherProducer.sendRecordJson(KafkaConfig.WEATHER_TOPIC_JSON, weatherApiJsonDto.getCountry(),
          weatherApiJsonDto);
    }
  }

  public List<WeatherApiCompactDto> consumeCompactWeatherData() {
    return weatherConsumer.consumeOnce();
  }

  public List<String> consumeWeatherDataJson() {
    return weatherConsumer.consumeJson();
  }

  public List<CurrentDto> readAll() {
    return weatherRepository.readAll();
  }

  public void aggregateWeatherData() {
    final Properties properties = kafkaConfig.loadConsumerJsonConfig();
    final StreamsBuilder streamsBuilder = new StreamsBuilder();

    streamsBuilder.<String, WeatherApiJsonDto>stream(KafkaConfig.WEATHER_TOPIC_JSON)
        .groupBy((key, value) -> key)
        .aggregate(WeatherService::getAverageTemp, (key, value, aggregator) -> {
          aggregator.getTemps().add(value.getTemp());
          setNewAverage(aggregator);
          return aggregator;
        }, Materialized.<String, AverageTemp, KeyValueStore<Bytes, byte[]>>as(
                KafkaConfig.WEATHER_AGGREGATED_TOPIC)
            .withKeySerde(Serdes.String())
            .withValueSerde(new AverageTempSerde()))
        .toStream()
        .mapValues(this::getAverage)
        .to(KafkaConfig.WEATHER_AGGREGATED_RESULT_TOPIC,
            Produced.with(Serdes.String(), Serdes.String()));

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
    final double sum = aggregator.getTemps().stream()
        .mapToDouble(p -> p)
        .sum();
    aggregator.setAverage(sum / (double) aggregator.getTemps().size());
  }

  private String getAverage(final AverageTemp temp) {
    // 'Cast' to String because our Double values get messed up when serializing into the kafka
    final Double average = temp.getAverage();
    return average.toString();
  }


}
