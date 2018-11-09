import { NgModule, OnInit } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouteReuseStrategy } from '@angular/router';

import { IonicModule, IonicRouteStrategy, Platform } from '@ionic/angular';
import { SplashScreen } from '@ionic-native/splash-screen/ngx';
import { StatusBar } from '@ionic-native/status-bar/ngx';
import '@teamhive/capacitor-sentry';
import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { Plugins } from '@capacitor/core';
const { Sentry } = Plugins;
@NgModule({
  declarations: [AppComponent],
  entryComponents: [],
  imports: [BrowserModule, IonicModule.forRoot(), AppRoutingModule],
  providers: [
    StatusBar,
    SplashScreen,
    { provide: RouteReuseStrategy, useClass: IonicRouteStrategy }
  ],
  bootstrap: [AppComponent]
})
export class AppModule implements OnInit{
  constructor(private platform: Platform) {}

  ngOnInit(): void {
    if (this.platform.is('ios')) {
      Sentry.initialize({
        dsn: 'https://0ffc67313d26438c9f036d4254e9ddb7@sentry.io/1319171'
      });
    } else if (this.platform.is('android')) {
      Sentry.initialize({
        dsn: 'https://518bc90005ec4760895b7253210e8315@sentry.io/1319173'
      });
    }
  }
}
