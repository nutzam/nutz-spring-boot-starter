import type { App } from 'vue';
import { default as aclApi, type AclApi } from './acl/mods';

import { default as authApi, type AuthApi } from './auth/mods';

import { default as codeApi, type CodeApi } from './code/mods';

export interface Api {
  aclApi: AclApi;
  authApi: AuthApi;
  codeApi: CodeApi;
  install: (app: App) => void;
}
export const api: Api = {
  aclApi,
  authApi,
  codeApi,
  install: (app: App) => {
    app.config.globalProperties.$api = api;
  },
};
