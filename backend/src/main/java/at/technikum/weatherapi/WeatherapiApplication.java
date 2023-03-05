package at.technikum.weatherapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
public class WeatherapiApplication {

  public static void main(String[] args) {
    SpringApplication.run(WeatherapiApplication.class, args);
  }

}
