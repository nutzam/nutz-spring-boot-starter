import {PluginObject} from 'vue';
import api, {Api} from './mods/index';

export const ApiPlugin: PluginObject<Api> = {
  install: Vue => {
    Object.defineProperties(Vue.prototype, {
      $api: {
        get() {
          return api;
        },
      },
    });
  },
};
