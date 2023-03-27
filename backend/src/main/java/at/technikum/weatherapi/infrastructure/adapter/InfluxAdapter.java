package at.technikum.weatherapi.infrastructure.adapter;

import at.technikum.weatherapi.domain.repository.WeatherRepository;
import at.technikum.weatherapi.infrastructure.adapter.model.CurrentDto;
import at.technikum.weatherapi.infrastructure.adapter.model.WeatherApiDto;
import at.technikum.weatherapi.infrastructure.config.influx.InfluxConfig;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.QueryApi;
import com.influxdb.client.WriteApiBlocking;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;
import lombok.RequiredArgsConstructor;
import org.influxdb.impl.InfluxDBResultMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class InfluxAdapter implements WeatherRepository {

    private final InfluxConfig influxConfig;
    @Override
    public CurrentDto save(CurrentDto currentDto) {
        final InfluxDBClient influxDBClient = influxConfig.getClient();
        final WriteApiBlocking writeApi = influxDBClient.getWriteApiBlocking();
        writeApi.writeMeasurement(WritePrecision.MS, currentDto);
        return currentDto;
    }

    @Override
    public List<CurrentDto> readAll() {
        final InfluxDBClient influxDBClient = influxConfig.getClient();
        final String flux = String.format( "from(bucket:\"%s\") " +
                "|> range(start:0) " +
                "|> filter(fn: (r) => r[\"_measurement\"] == \"currentdto\") ", "weather");
        QueryApi queryApi = influxDBClient.getQueryApi();

        final var result = queryApi.query(flux);
        final var resultMapped = queryApi.query(flux, CurrentDto.class);

        return resultMapped;
    }
}
