import { config, type GlobalConfig } from '@/core/settings';
import { defineStore } from 'pinia';

export const useAppStore = defineStore('app', {
  state: (): GlobalConfig => JSON.parse(JSON.stringify(config)),
  actions: {
    updateConf(conf: unknown) {
      Object.assign(this, { layout: conf });
    },
    collapsedChange(collapsed: boolean) {
      this.layout.collapsed = collapsed;
    },
    changePrimaryColor(color: string) {
      this.theme.primaryColor = color;
    },
    changeLocale(lang: string) {
      this.language = lang;
    },
  },
  persist: {
    enabled: true,
    strategies: [
      {
        key: 'app',
        storage: localStorage,
      },
    ],
  },
});
