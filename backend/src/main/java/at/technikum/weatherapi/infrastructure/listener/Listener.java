package at.technikum.weatherapi.infrastructure.listener;

import at.technikum.weatherapi.infrastructure.adapter.model.WeatherApiDto;
import at.technikum.weatherapi.infrastructure.config.JsonMapper;
import at.technikum.weatherapi.infrastructure.config.KafkaConstants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class Listener {

    @Autowired
    private SimpMessagingTemplate webSocket;

    @KafkaListener(topics = KafkaConstants.WEATHER_TOPIC)
    public void processMessage(ConsumerRecord<String, String> cr, @Payload String content) {

            this.webSocket.convertAndSend(KafkaConstants.WEBSOCKET_DESTINATION, content);

    }

}
