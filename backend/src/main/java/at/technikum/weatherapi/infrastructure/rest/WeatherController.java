package at.technikum.weatherapi.infrastructure.rest;

import at.technikum.weatherapi.WeatherApiCompactDto;
import at.technikum.weatherapi.application.WeatherService;
import at.technikum.weatherapi.infrastructure.adapter.model.WeatherApiDto;
import at.technikum.weatherapi.infrastructure.config.JsonDeserializer;
import at.technikum.weatherapi.infrastructure.config.JsonMapper;
import at.technikum.weatherapi.infrastructure.config.KafkaConfig;
import at.technikum.weatherapi.infrastructure.rest.model.WeatherApiResponseDto;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import org.apache.avro.data.Json;
import org.apache.avro.specific.SpecificRecordBase;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/weather")
public class WeatherController {

  private final WeatherService weatherService;

  @CrossOrigin
  @GetMapping
  public void publishWeatherData() {
    weatherService.publishCompactWeatherData();
  }

  @CrossOrigin
  @GetMapping("/json")
  public void publishWeatherDataJson() {
    weatherService.publishCompactWeatherDataJson();
  }

  @CrossOrigin
  @GetMapping("/consume")
  public List<WeatherApiResponseDto> consumeWeatherData() {
    final List<WeatherApiCompactDto> dtos = weatherService.consumeCompactWeatherData();
    final List<WeatherApiResponseDto> response = dtos.stream()
        .map(dto -> {
          final WeatherApiResponseDto responseElement = new WeatherApiResponseDto();
          responseElement.setCountry(dto.getCountry());
          responseElement.setTemp(dto.getTemp());
          responseElement.setLocationName(dto.getLocationName());
          responseElement.setTemp(dto.getFeelslikeTemp());
          return responseElement;
        })
        .toList();
    return response;
  }

  @CrossOrigin
  @GetMapping("/consume/json")
  public List<String> consumeWeatherDataJson() {
    return weatherService.consumeWeatherDataJson();
  }

  @CrossOrigin
  @GetMapping("/aggregate")
  public void aggregateWeatherData() {
    weatherService.aggregateWeatherData();
  }
/*
  @GetMapping("/generate")
  public String generateAvro() throws JsonMappingException {
    ObjectMapper mapper = new ObjectMapper(new AvroFactory());
    mapper.findAndRegisterModules();
    AvroSchemaGenerator gen = new AvroSchemaGenerator();
    mapper.acceptJsonFormatVisitor(WeatherApiDto.class, gen);
    AvroSchema schemaWrapper = gen.getGeneratedSchema();

    org.apache.avro.Schema avroSchema = schemaWrapper.getAvroSchema();
    String asJson = avroSchema.toString(true);
    return asJson;
  }

 */
}
