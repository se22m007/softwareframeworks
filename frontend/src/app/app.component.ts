import { HttpClient } from '@angular/common/http';
import { Component, Inject, Injectable, OnInit } from '@angular/core';
import { NgxsOnInit, Select, Store } from '@ngxs/store';
import { ConnectWebSocket } from '@ngxs/websocket-plugin';
import { Observable } from 'rxjs';
import { KafkaState } from './state/kafka.state';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.sass']
})
export class AppComponent implements OnInit {
  title = 'frontend';

  @Select(KafkaState.messages)
  kafkaMessages$: Observable<string[]>

  constructor(private http: HttpClient, private store: Store) {}

  public weatherData:WeatherData[] = [
    { city: "Vienna", temperature: 10.5 },
    { city: "Rome", temperature: 15.2 },
    { city: "Capetown", temperature: 26.3 }
  ]

  displayedColumns: string[] = ['city', 'temperature'];
  dataSource = this.weatherData;

  public callProducerEndpoint() {
    this.http.get("http://localhost:8080/weather")
      .subscribe(() => {});
  }

  ngOnInit(){
    this.store.dispatch(new ConnectWebSocket())
  }

}

interface WeatherData {
  city: string;
  temperature: number;
}
