import type { App } from 'vue';
import aclApi from './acl/mods';

import authApi from './auth/mods';

import codeApi from './code/mods';

export const api = {
  aclApi,
  authApi,
  codeApi,
  install: (app: App) => {
    app.config.globalProperties.$api = api;
  },
};
export default api;
