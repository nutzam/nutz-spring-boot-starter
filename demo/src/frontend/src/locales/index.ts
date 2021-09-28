import Vue from "vue";
import VueI18n, { TranslateResult } from "vue-i18n";
import moment from "moment";
import vxezhCN from "vxe-table/lib/locale/lang/zh-CN";
import vxeenUS from "vxe-table/lib/locale/lang/en-US";
// default lang
import zhCN from "./lang/zh-CN";
import enUS from "./lang/en-US";

Vue.use(VueI18n);

export const defaultLang = "zh-CN";

const messages = {
  "zh-CN": {
    ...zhCN,
    ...vxezhCN,
  },
  en_US: {
    ...enUS,
    ...vxeenUS,
  },
};

const i18n = new VueI18n({
  silentTranslationWarn: true,
  locale: defaultLang,
  fallbackLocale: defaultLang,
  messages,
});

const loadedLanguages = [defaultLang];

function setI18nLanguage(lang: string): string {
  i18n.locale = lang;
  // request.headers['Accept-Language'] = lang
  const html = document.querySelector("html");
  html && html.setAttribute("lang", lang);
  return lang;
}

export function loadLanguageAsync(lang = defaultLang): Promise<string> {
  return new Promise((resolve) => {
    // 缓存语言设置
    if (i18n.locale !== lang) {
      if (!loadedLanguages.includes(lang)) {
        return import(
          /* webpackChunkName: "lang-[request]" */ `./lang/${lang}`
        ).then((msg) => {
          const locale = msg.default;
          i18n.setLocaleMessage(lang, locale);
          loadedLanguages.push(lang);
          moment.updateLocale(locale.momentName, locale.momentLocale);
          return setI18nLanguage(lang);
        });
      }
      return resolve(setI18nLanguage(lang));
    }
    return resolve(lang);
  });
}

export function i18nRender(key: string): TranslateResult {
  return i18n.t(`${key}`);
}

export default i18n;
