package at.technikum.weatherapi.infrastructure.adapter;

import at.technikum.weatherapi.infrastructure.adapter.model.WeatherApiDto;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class WeatherApiAdapter {

  private final RestTemplate restTemplate = new RestTemplate();

  private final String apiKey;

  private final String apiUrl;

  public WeatherApiAdapter(@Value("${api.key}") final String apiKey,
                           @Value("${api.url}") final String apiUrl) {
    this.apiKey = apiKey;
    this.apiUrl = apiUrl;
  }

  public WeatherApiDto getWeatherData(final String city) {
    final String url = UriComponentsBuilder.fromHttpUrl(apiUrl)
        .queryParam("key", apiKey)
        .queryParam("q", city)
        .queryParam("days", 2)
        .toUriString();
    final ResponseEntity<WeatherApiDto> response =
        restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(null, null), WeatherApiDto.class);

    return response.getBody();
  }
}
