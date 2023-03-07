package at.technikum.weatherapi.infrastructure.config;

public final class KafkaConstants {

    public static final String WEATHER_TOPIC = "weather";
    public static final String WEBSOCKET_DESTINATION = "/topic/weather";
    private KafkaConstants() {}
}
