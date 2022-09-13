import type { App } from 'vue';
import piniaPlugin from './pinia';
import i18n from '@/locales/index';
import axiosPlugin from './axios';
import vxeTable from './vxe-table';
import { api } from '@/api';
import formCreatePlugin from './form-create';
export default {
  install: (app: App) => {
    app.use(piniaPlugin).use(i18n).use(axiosPlugin).use(api).use(formCreatePlugin).use(vxeTable);
  },
};
