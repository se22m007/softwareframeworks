package at.technikum.weatherapi.infrastructure.config.serdes;

import at.technikum.weatherapi.domain.model.AverageTemp;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;

public final class CustomSerdes {
  public CustomSerdes() {

  }

  public static Serde<AverageTemp> averageTemp() {
    JsonSerializer<AverageTemp> serializer = new JsonSerializer<>();
    JsonDeserializer<AverageTemp> deserializer = new JsonDeserializer<>(AverageTemp.class);
    return Serdes.serdeFrom(serializer, deserializer);
  }
}