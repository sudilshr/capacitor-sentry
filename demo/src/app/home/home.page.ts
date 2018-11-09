import { Component } from '@angular/core';
import { Plugins } from '@capacitor/core';
import * as sentry from '@teamhive/capacitor-sentry';
const { Sentry } = Plugins;
@Component({
  selector: 'app-home',
  templateUrl: 'home.page.html',
  styleUrls: ['home.page.scss']
})
export class HomePage {
  crash(event) {
    Sentry.crash();
  }
  setUser(event) {
    Sentry.setUser({
      email: 'fortune.osei@yahoo.com',
      id: '1234',
      data: {
        name: 'Osei Forune'
      }
    });
  }

  setTags(event) {
    Sentry.setTags({
      tags: {
        job: 'Dev'
      }
    });
  }

  setExtra() {
    Sentry.setExtra({
      extra: {
        framework: 'Capacitor!!!'
      }
    });
  }
  clearContext(event) {
    Sentry.clearContext();
  }

  captureMessage(event) {
    Sentry.captureMessage({ message: 'Testing Capture Message' });
  }

  captureException(event) {
    Sentry.captureException({
      exception: sentry.SentryException.fromError(new Error('Testing Capture Execption'))
    });
  }

  captureBreadcrumb(event) {
    Sentry.captureBreadcrumb({
      level: sentry.SentryLevel.INFO,
      category: 'IDK',
      data: {
        stuff: '?'
      },
      message: 'Testing',
      timeStamp: new Date().toISOString()
    });
  }
}
