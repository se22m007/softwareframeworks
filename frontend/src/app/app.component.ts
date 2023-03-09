import { HttpClient } from '@angular/common/http';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subject, takeUntil } from 'rxjs';
import { WeatherData } from './model';
import { RxStompService } from './rxstomp.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.sass']
})
export class AppComponent implements OnInit, OnDestroy {
  title = 'frontend';

  weatherData: WeatherData[];
  private destroy$ = new Subject();

  constructor(private http: HttpClient, private rxStompService: RxStompService) {}

  displayedColumns: string[] = ['location.name'];

  public callProducerEndpoint() {
    this.http.get("http://localhost:8080/weather")
      .subscribe(() => {});
  }

  ngOnInit(){
    this.weatherData = [];

    this.rxStompService.watch('/topic/weather')
      .pipe(
        takeUntil(this.destroy$)
      ).subscribe((message) => {
        var result = JSON.parse(message.body) as WeatherData;
        console.log('Received from websocket: ' + message.body);
        this.weatherData.push(result);
      });
  }

  ngOnDestroy(): void {
    this.destroy$.next(null);
    this.destroy$.unsubscribe();
  }

}
