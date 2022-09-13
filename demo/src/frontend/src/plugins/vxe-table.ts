/* eslint-disable @typescript-eslint/no-explicit-any */
import { plugin } from '@/core/plugin';
import i18n from '@/locales';
import type { App } from 'vue';
import VXETable from 'vxe-table';
import 'vxe-table/lib/style.css';

VXETable.setup(
  Object.assign(
    {
      // 对组件内置的提示语进行国际化翻译
      i18n: (key: string, args?: any) => i18n.global.t(key, args),
      //vxe-table 组件键值自动翻译
      translate(key: string, args?: any) {
        // 例如，只翻译 "page." 开头的键值
        if (key && key.indexOf('page.') > -1) {
          return i18n.global.t(key, args);
        }
        return key;
      },
    },
    plugin.vxe,
  ),
);

export default {
  install: (app: App) => {
    app.use(VXETable);
  },
};
