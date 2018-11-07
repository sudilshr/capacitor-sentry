declare global {
  interface PluginRegistry {
    Sentry?: SentryPlugin;
  }
}

export interface SentryPlugin {
  initialize(options: { dsn: string }): Promise<any>;
  crash(): Promise<any>;
  setUser(): Promise<any>;
  setTags(): Promise<any>;
  setExtra(): Promise<any>;
  clearContext(): Promise<any>;
  captureMessage(): Promise<any>;
  captureException(): Promise<any>;
  captureBreadcrumb(): Promise<any>;
}
