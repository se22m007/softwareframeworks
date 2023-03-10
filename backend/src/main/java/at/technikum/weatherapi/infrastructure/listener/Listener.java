package at.technikum.weatherapi.infrastructure.listener;

import at.technikum.weatherapi.infrastructure.config.KafkaConstants;
import lombok.*;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Listener {

    private final SimpMessagingTemplate webSocket;

    @KafkaListener(topics = KafkaConstants.WEATHER_TOPIC)
    public void processMessage(final ConsumerRecord<String, String> cr, @Payload final String content) {
        this.webSocket.convertAndSend(KafkaConstants.WEBSOCKET_DESTINATION, content);
    }

}
