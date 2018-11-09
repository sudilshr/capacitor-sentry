declare global {
  interface PluginRegistry {
    Sentry?: SentryPlugin;
  }
}

export enum SentryLevel {
  DEBUG = 'debug',
  INFO = 'info',
  WARNING = 'warning',
  ERROR = 'error',
  CRITICAL = 'critical'
}

export interface SentryPlugin {
  initialize(options: { dsn: string }): Promise<any>;
  crash(): Promise<any>;
  setUser(user: {
    email?: string;
    id?: string;
    ipAddress?: string;
    data: Object;
  }): Promise<any>;
  setTags(options: { tags: Object }): Promise<any>;
  setExtra(options: { extra: Object }): Promise<any>;
  clearContext(): Promise<any>;
  captureMessage(options: { message: string }): Promise<any>;
  captureException(options: { exception: Error }): Promise<any>;
  captureBreadcrumb(options: {
    level: SentryLevel;
    category: string;
    message: string;
    timeStamp: string;
    data: Object;
  }): Promise<any>;
}
