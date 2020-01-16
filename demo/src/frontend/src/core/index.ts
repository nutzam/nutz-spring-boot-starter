import Vue from 'vue';
import * as types from '@/store/mutation-types';
import {AppModule} from '@/store/modules/app';
import config from '@/config/defaultSettings';
import {message} from 'ant-design-vue';
import {UserModule} from '../store/modules/user';

export const colorList = [
  {
    key: 'dustRed',
    color: '#F5222D',
  },
  {
    key: 'volcano',
    color: '#FA541C',
  },
  {
    key: 'sunsetOrange',
    color: '#FAAD14',
  },
  {
    key: 'cyan',
    color: '#13C2C2',
  },
  {
    key: 'polarGreen',
    color: '#52C41A',
  },
  {
    key: 'daybreakBlue',
    color: '#1890FF',
  },
  {
    key: 'geekBlue',
    color: '#2F54EB',
  },
  {
    key: 'goldenPurple',
    color: '#722ED1',
  },
];

export const updateTheme = (
  showMessage: boolean,
  primaryColor: string,
  mess?: any
): void => {
  if (!primaryColor) {
    return;
  }
  let hideMessage: any;
  if (showMessage) {
    hideMessage = message.loading(mess, 0);
  }
  function buildTheme() {
    if (!window.less) {
      return;
    }
    setTimeout(
      () => {
        window.less
          .modifyVars({
            '@primary-color': primaryColor,
          })
          .then(() => {
            if (showMessage) {
              hideMessage();
            }
          })
          .catch((e: any) => {
            console.warn(e);
            if (showMessage) {
              hideMessage();
            }
          });
      },
      showMessage ? 200 : 0
    );
  }
  buildTheme();
};

export function Initializer(showMessage: boolean): void {
  updateTheme(
    showMessage,
    Vue.ls.get(types.DEFAULT_PRIMARY_COLOR, config.primaryColor)
  );
  AppModule.SET_SIDEBAR(Vue.ls.get(types.DEFAULT_SIDEBAR_TYPE, true));
  AppModule.TOGGLE_NAV_THEME(
    Vue.ls.get(types.DEFAULT_NAV_THEME, config.navTheme)
  );
  AppModule.TOGGLE_LAYOUT_MODE(
    Vue.ls.get(types.DEFAULT_LAYOUT_MODE, config.layoutMode)
  );
  AppModule.TOGGLE_FIXED_HEADER(
    Vue.ls.get(types.DEFAULT_FIXED_HEADER, config.fixedHeader)
  );
  AppModule.TOGGLE_FIXED_SIDERBAR(
    Vue.ls.get(types.DEFAULT_FIXED_SIDEMENU, config.autoHideHeader)
  );
  AppModule.TOGGLE_CONTENT_WIDTH(
    Vue.ls.get(types.DEFAULT_CONTENT_WIDTH_TYPE, config.contentWidth)
  );
  AppModule.TOGGLE_FIXED_HEADER_HIDDEN(
    Vue.ls.get(types.DEFAULT_FIXED_HEADER_HIDDEN, config.autoHideHeader)
  );
  AppModule.TOGGLE_COLOR_WEAK(
    Vue.ls.get(types.DEFAULT_COLOR_WEAK, config.colorWeak)
  );
  AppModule.TOGGLE_PRIMARY_COLOR(
    Vue.ls.get(types.DEFAULT_PRIMARY_COLOR, config.primaryColor)
  );
  AppModule.TOGGLE_MULTI_TAB(
    Vue.ls.get(types.DEFAULT_MULTI_TAB, config.multiTab)
  );
  AppModule.TOGGLE_LANGUAGE(
    Vue.ls.get(types.DEFAULT_LANGUAGE, config.language)
  );
  UserModule.login(Vue.ls.get(types.LOGIN_USER, {}));
}

export const updateColorWeak = (colorWeak: boolean) => {
  colorWeak
    ? document.body.classList.add('colorWeak')
    : document.body.classList.remove('colorWeak');
};
