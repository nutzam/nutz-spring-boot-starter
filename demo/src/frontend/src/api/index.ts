import { PluginObject } from "vue";

import { default as aclApi, AclApi } from "./acl/mods";

import { default as dictionaryApi, DictionaryApi } from "./dictionary/mods";

import { default as loginApi, LoginApi } from "./login/mods";

export interface Api {
  aclApi: AclApi;
  dictionaryApi: DictionaryApi;
  loginApi: LoginApi;
}

export const ApiPlugin: PluginObject<Api> = {
  install: (Vue) => {
    Object.defineProperties(Vue.prototype, {
      $api: {
        get() {
          return {
            aclApi,
            dictionaryApi,
            loginApi,
          };
        },
      },
    });
  },
};
