# Capacitor Sentry

[![npm](https://img.shields.io/npm/v/@teamhive/capacitor-sentry.svg)](https://www.npmjs.com/package/teamhive/capacitor-sentry)
[![npm](https://img.shields.io/npm/dt/@teamhive/capacitor-sentry.svg?label=npm%20downloads)](https://www.npmjs.com/package/teamhive/capacitor-sentry)

## Installation

- `npm i @teamhive/capacitor-sentry`

## Usage


```ts
import '@teamhive/capacitor-sentry';

import { Plugins } from '@capacitor/core';
const { Sentry } = Plugins;
Sentry.init();

```

## Api

| Method                                               | Default | Type                      | Description                 |
| ---------------------------------------------------- | ------- | ------------------------- | --------------------------- |
| init(options: { dsn: string }) |         | `Promise<any>` |  |
| crash() |         |  |  Call to crash app  |
| setUser({id:string,email:string,username:string,ip:string,extra:Object }) |         |  |    |
| setTags({tags:Object}) |         |  |  |
| setExtra({extra:Object}) |         |  |  |
| clearContext() |         |  |  |
| captureMessage({message:string}) |         |  |  |
| captureException() |         |  |  |
| captureBreadcrumb({}) |         |  |  |
