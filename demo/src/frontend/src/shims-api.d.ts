import type { Api } from './api';

declare global {
  interface Window {
    api: Api;
  }
}
declare module 'vue' {
  interface ComponentCustomProperties {
    $api: Api;
  }
}
