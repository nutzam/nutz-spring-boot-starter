import { PluginObject } from 'vue';

import { default as aclApi, AclApi } from './acl/mods';

import { default as dictionaryApi, DictionaryApi } from './dictionary/mods';

import { default as loginApi, LoginApi } from './login/mods';

import { default as wechatApi, WechatApi } from './wechat/mods';

export interface Api {
  aclApi: AclApi;
  dictionaryApi: DictionaryApi;
  loginApi: LoginApi;
  wechatApi: WechatApi;
}

export const ApiPlugin: PluginObject<Api> = {
  install: Vue => {
    Object.defineProperties(Vue.prototype, {
      $api: {
        get() {
          return {
            aclApi,
            dictionaryApi,
            loginApi,
            wechatApi,
          };
        },
      },
    });
  },
};
