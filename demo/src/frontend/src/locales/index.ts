import { createI18n, useI18n, type LocaleMessages, type VueMessageType } from 'vue-i18n';
import { config } from '@/core/settings';

import zh from './zh';
import en from './en';

import en_US_antd from 'ant-design-vue/es/locale/en_US';
import zh_CN_antd from 'ant-design-vue/es/locale/zh_CN';

import zhCN from 'vxe-table/lib/locale/lang/zh-CN';
import enUS from 'vxe-table/lib/locale/lang/en-US';
export const messages = {
  zh_CN: {
    ...zh,
    ...zh_CN_antd,
    ...zhCN,
  },
  en_US: {
    ...en,
    ...en_US_antd,
    ...enUS,
  },
} as unknown as LocaleMessages<VueMessageType>;

const i18n = createI18n({
  legacy: false,
  globalInjection: true,
  locale: config.language,
  messages,
});

export function setI18nLanguage(lang: string): string {
  useI18n().locale.value = lang;
  const html = document.querySelector('html');
  html && html.setAttribute('lang', lang);
  return lang;
}
/** 翻译 */
export function translate(key: string): string {
  return i18n.global.t(key);
}

export default i18n;
