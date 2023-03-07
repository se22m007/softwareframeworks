import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatTableModule } from '@angular/material/table'; 
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button'; 

import { HttpClientModule } from '@angular/common/http';

import { RxStompConfig } from '@stomp/rx-stomp';

import { NgxsWebsocketPluginModule } from '@ngxs/websocket-plugin'
import { NgxsModule } from '@ngxs/store'
import { KafkaState } from './state/kafka.state';
import { myRxStompConfig } from './rx-stomp.config';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { rxStompServiceFactory } from './rx-stomp-service-factory';
import { RxStompService } from './rxstomp.service';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
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
  providers: [
    {
       provide: RxStompConfig,
       useValue: myRxStompConfig
    },
    {
       provide: RxStompService,
       useFactory: rxStompServiceFactory,
       deps: [RxStompConfig]
    }
 ],
  bootstrap: [AppComponent]
})
export class AppModule { }
