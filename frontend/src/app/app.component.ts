import { HttpClient } from '@angular/common/http';
import { Component, Inject, Injectable, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { NgxsOnInit, Select, Store } from '@ngxs/store';
import { ConnectWebSocket } from '@ngxs/websocket-plugin';
import { Observable, Subject, takeUntil } from 'rxjs';
import { RxStompService } from './rxstomp.service';
import { KafkaState } from './state/kafka.state';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.sass']
})
export class AppComponent implements OnInit, OnDestroy {
  title = 'frontend';

  myForm: FormGroup;
  messages: string[];
  private destroy$ = new Subject();

  @Select(KafkaState.messages)
  kafkaMessages$: Observable<string[]>

  constructor(private http: HttpClient, private rxStompService: RxStompService, private store: Store) {}

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
    this.messages = [];

    this.rxStompService.watch('/topic/weather')
      .pipe(
        takeUntil(this.destroy$)
      ).subscribe((message) => {
        console.log('Received from websocket: ' + message.body);
        this.messages.push(message.body);
        this.messages = this.messages.slice(-5);
      });
  }

  ngOnDestroy(): void {
    this.destroy$.next(null);
    this.destroy$.unsubscribe();
  }

}

interface WeatherData {
  city: string;
  temperature: number;
}
