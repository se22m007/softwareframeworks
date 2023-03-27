package at.technikum.weatherapi.domain.repository;

import at.technikum.weatherapi.infrastructure.adapter.model.CurrentDto;
import at.technikum.weatherapi.infrastructure.adapter.model.WeatherApiDto;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Not in use yet.
 */
@Repository
public interface WeatherRepository {
    CurrentDto save(CurrentDto weatherApiDto);

    List<CurrentDto> readAll();
}
