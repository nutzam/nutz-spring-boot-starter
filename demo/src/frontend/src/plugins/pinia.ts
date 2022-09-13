import type { App } from 'vue';
import { createPinia } from 'pinia';
import piniaPersist from 'pinia-plugin-persist';

const store = createPinia();
store.use(piniaPersist);

export default {
  install: (app: App) => {
    app.use(store);
  },
};
