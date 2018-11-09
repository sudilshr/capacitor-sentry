import { Component } from '@angular/core';

import { Platform } from '@ionic/angular';
import { SplashScreen } from '@ionic-native/splash-screen/ngx';
import { StatusBar } from '@ionic-native/status-bar/ngx';
import { Plugins } from '@capacitor/core';
const { Sentry } = Plugins;
@Component({
  selector: 'app-root',
  templateUrl: 'app.component.html'
})
export class AppComponent {
  constructor(
    private platform: Platform,
    private splashScreen: SplashScreen,
    private statusBar: StatusBar
  ) {
    this.initializeApp();
  }

  initializeApp() {
    this.platform.ready().then(() => {
      this.statusBar.styleDefault();
      this.splashScreen.hide();
      if (this.platform.is('ios')) {
        Sentry.initialize({
          dsn: 'https://0ffc67313d26438c9f036d4254e9ddb7@sentry.io/1319171'
        });
      } else if (this.platform.is('android')) {
        Sentry.initialize({
          dsn: 'https://518bc90005ec4760895b7253210e8315@sentry.io/1319173'
        });
      }
    });
  }
}
