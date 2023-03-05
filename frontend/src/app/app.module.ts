import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatTableModule } from '@angular/material/table'; 
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button'; 

import { HttpClientModule } from '@angular/common/http';

import { NgxsWebsocketPluginModule } from '@ngxs/websocket-plugin'
import { NgxsModule } from '@ngxs/store'
import { KafkaState } from './state/kafka.state';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    HttpClientModule,
    MatTableModule,
    MatToolbarModule,
    MatButtonModule,
    NgxsModule.forRoot([
      KafkaState
    ]),
    NgxsWebsocketPluginModule.forRoot({
      url: 'ws://localhost:9092/websocket'
    })
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
