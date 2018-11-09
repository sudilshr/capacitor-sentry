import { WebPlugin } from '@capacitor/core';
import { SentryPlugin } from './definitions';

export class SentryPluginWeb extends WebPlugin implements SentryPlugin {
  constructor() {
    super({
      name: 'Sentry',
      platforms: ['web']
    });
  }

  initialize(options: { dsn: string }): Promise<any> {
    console.log('hasOptions:', !!options);
    return Promise.resolve();
  }
  crash(): Promise<any> {
    return Promise.resolve();
  }
  setUser(): Promise<any> {
    return Promise.resolve();
  }
  setTags(): Promise<any> {
    return Promise.resolve();
  }
  setExtra(): Promise<any> {
    return Promise.resolve();
  }
  clearContext(): Promise<any> {
    return Promise.resolve();
  }
  captureMessage(): Promise<any> {
    return Promise.resolve();
  }
  captureException(): Promise<any> {
    return Promise.resolve();
  }
  captureBreadcrumb(): Promise<any> {
    return Promise.resolve();
  }
}

const Sentry = new SentryPluginWeb();

export { Sentry };
