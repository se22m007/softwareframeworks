package at.technikum.weatherapi.infrastructure.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonMapper {

  public static String deserialize(final Object object) {
    try {
      final ObjectMapper objectMapper = new ObjectMapper();
      objectMapper.findAndRegisterModules();
      ObjectWriter ow = objectMapper.writer().withDefaultPrettyPrinter();
      return ow.writeValueAsString(object);
    } catch (final JsonProcessingException exception) {
      log.info(exception.getMessage());
      log.info("Error when deserializing");
      throw new IllegalArgumentException("???");
    }
  }
}