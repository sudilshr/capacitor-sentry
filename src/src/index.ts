export * from './definitions';
export * from './web';

export class SentryException {
  public static fromError(error: Error) {
    return {
      name: error.stack,
      message: error.message,
      stack: error.stack
    };
  }
}
