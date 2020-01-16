import Vue, {VueConstructor} from 'vue';
import {AxiosInstance} from 'axios';
import {Api} from './api/mods/index';

declare global {
  interface Window {
    axios: AxiosInstance;
    api: Api;
  }
}

declare module 'vue/types/vue' {
  interface Vue {
    $axios: AxiosInstance;
    $api: Api;
  }
  interface VueConstructor {
    $axios: AxiosInstance;
    $api: Api;
  }
}
