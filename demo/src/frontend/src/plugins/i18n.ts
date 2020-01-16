import Vue from 'vue';
import VueI18n from 'vue-i18n';
import enUS from '@/locale/enUS';
import zhCN from '@/locale/zhCN';
import zhCNLocat from 'vxe-table/lib/locale/lang/zh-CN';
import enLocat from 'vxe-table/lib/locale/lang/en';
Vue.use(VueI18n);

export default new VueI18n({
  locale: 'zhCN',
  messages: {
    enUS: {...enUS, ...enLocat},
    zhCN: {...zhCN, ...zhCNLocat},
  },
});
