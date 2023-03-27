package at.technikum.weatherapi.infrastructure.config.kafka.serdes;

import at.technikum.weatherapi.domain.model.AverageTemp;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

public class AverageTempSerde implements Serde<AverageTemp> {
  public AverageTempSerde() {

  }

  @Override
  public Serializer<AverageTemp> serializer() {
    return new JsonSerializer<>();
  }

  @Override
  public Deserializer<AverageTemp> deserializer() {
    return new JsonDeserializer<>(AverageTemp.class);
  }


}
