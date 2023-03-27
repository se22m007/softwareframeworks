package at.technikum.weatherapi.infrastructure.config.influx;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InfluxConfig {

    private final String url;
    private final String org;
    private final String token;
    private final String bucket;

    public InfluxConfig(@Value("${spring.influx.url}") final String url,
                        @Value("${spring.influx.org}") final String org,
                        @Value("${spring.influx.token}") final String token,
                        @Value("${spring.influx.bucket}")final String bucket) {
        this.url = url;
        this.org = org;
        this.token = token;
        this.bucket = bucket;
    }

    public InfluxDBClient getClient() {
        return InfluxDBClientFactory.create(url, token.toCharArray(), org, bucket);
    }
}
