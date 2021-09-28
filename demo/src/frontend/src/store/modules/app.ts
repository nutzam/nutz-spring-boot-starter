import storage from "store";
import { loadLanguageAsync } from "@/locales";

import {
  VuexModule,
  Module,
  Action,
  Mutation,
  getModule,
} from "vuex-module-decorators";
import { DEVICE_TYPE } from "@/utils/device";
import * as types from "@/store/mutation-types";
import config from "@/core/config";

import store from "@/store";

export interface AppState {
  sideCollapsed: boolean;
  isMobile: boolean;
  theme: string;
  layout: string;
  contentWidth: string;
  fixedHeader: boolean;
  fixedSidebar: boolean;
  autoHideHeader: boolean;
  color: string;
  weak: boolean;
  multiTab: boolean;
  lang: string;
  _antLocale: Record<string, unknown>;

  name: string;
  title: string;
  description: string;
  copyright: string;
  uuid: string;
}

@Module({ dynamic: true, store, name: "app" })
class App extends VuexModule implements AppState {
  public sideCollapsed =
    storage.get(types.SIDEBAR_TYPE) || config.sideCollapsed;
  public isMobile = false;
  public theme = storage.get(types.NAV_THEME) || config.navTheme;
  public layout = storage.get(types.TOGGLE_LAYOUT) || config.layout;
  public contentWidth =
    storage.get(types.CONTENT_WIDTH_TYPE) || config.contentWidth;
  public fixedHeader =
    storage.get(types.TOGGLE_FIXED_HEADER) || config.fixedHeader;
  public fixedSidebar =
    storage.get(types.TOGGLE_FIXED_SIDEBAR) || config.fixSiderbar;
  public autoHideHeader =
    storage.get(types.TOGGLE_HIDE_HEADER) || config.autoHideHeader;
  public color = storage.get(types.TOGGLE_COLOR) || config.primaryColor;
  public weak = storage.get(types.TOGGLE_WEAK) || config.colorWeak;
  public multiTab = storage.get(types.TOGGLE_MULTI_TAB) || config.multiTab;
  public lang = storage.get(types.APP_LANGUAGE) || config.language;

  public _antLocale = {};

  public name = config.name;
  public title = config.title;
  public description = config.description;
  public copyright = config.copyright;
  public showDrawer = config.showDrawer;
  public uuid = Math.random().toString();

  @Mutation
  public SIDEBAR_TYPE(type: boolean) {
    this.sideCollapsed = type;
    storage.set(types.SIDEBAR_TYPE, type);
  }

  @Mutation
  public TOGGLE_MOBILE_TYPE(isMobile: boolean) {
    this.isMobile = isMobile;
  }

  @Mutation
  public TOGGLE_NAV_THEME(theme: string) {
    this.theme = theme;
    storage.set(types.TOGGLE_NAV_THEME, theme);
  }

  @Mutation
  public TOGGLE_LAYOUT(mode: string) {
    this.layout = mode;
    storage.set(types.TOGGLE_LAYOUT, mode);
  }

  @Mutation
  public TOGGLE_FIXED_HEADER(fixedHeader: boolean) {
    this.fixedHeader = fixedHeader;
    storage.set(types.TOGGLE_FIXED_HEADER, fixedHeader);
  }

  @Mutation
  public TOGGLE_FIXED_SIDEBAR(fixedSidebar: boolean) {
    this.fixedSidebar = fixedSidebar;
    storage.set(types.TOGGLE_FIXED_SIDEBAR, fixedSidebar);
  }

  @Mutation
  public TOGGLE_CONTENT_WIDTH(contentWidth: string) {
    this.contentWidth = contentWidth;
    storage.set(types.TOGGLE_CONTENT_WIDTH, contentWidth);
  }

  @Mutation
  public TOGGLE_HIDE_HEADER(show: boolean) {
    this.autoHideHeader = show;
    storage.set(types.TOGGLE_HIDE_HEADER, show);
  }

  @Mutation
  public TOGGLE_COLOR(color: string) {
    this.color = color;
    storage.set(types.TOGGLE_COLOR, color);
  }

  @Mutation
  public TOGGLE_WEAK(weak: boolean) {
    storage.set(types.TOGGLE_WEAK, weak);
    this.weak = weak;
  }
  @Mutation
  public TOGGLE_MULTI_TAB(multiTab: boolean) {
    storage.set(types.TOGGLE_MULTI_TAB, multiTab);
    this.multiTab = multiTab;
  }

  @Mutation
  public APP_LANGUAGE(lang: string, antd = {}) {
    this.lang = lang;
    this._antLocale = antd;
    storage.set(types.APP_LANGUAGE, lang);
  }

  @Mutation
  public refresh() {
    console.log("refresh");
    this.uuid = Math.random().toString();
  }

  @Action({ commit: "APP_LANGUAGE" })
  public setLang(lang: string) {
    return new Promise((resolve, reject) => {
      loadLanguageAsync(lang)
        .then(() => {
          resolve(lang);
        })
        .catch((e) => {
          reject(e);
        });
    });
  }

  @Action({ commit: "SET_SIDEBAR" })
  public SetSidebar(type: boolean) {
    return type;
  }

  @Action
  public CloseSidebar() {
    this.context.commit("CLOSE_SIDEBAR");
  }

  @Action({ commit: "TOGGLE_NAV_THEME" })
  public ToggleNavTheme(theme: string) {
    return theme;
  }

  @Action({ commit: "TOGGLE_COLOR" })
  public TogglePrimaryColor(color: string) {
    return color;
  }

  @Action({ commit: "TOGGLE_CONTENT_WIDTH" })
  public ToggleContentWidth(contentWidth: string) {
    return contentWidth;
  }

  @Action({ commit: "TOGGLE_DEVICE" })
  public ToggleDevice(device: DEVICE_TYPE) {
    return device;
  }

  @Action({ commit: "TOGGLE_LAYOUT" })
  public ToggleLayoutMode(layoutMode: string) {
    return layoutMode;
  }

  @Action({ commit: "TOGGLE_MULTI_TAB" })
  public ToggleMultiTab(payload: boolean) {
    return payload;
  }

  @Action({ commit: "TOGGLE_FIXED_HEADER_HIDDEN" })
  public ToggleFixedHeaderHidden(show: boolean) {
    return show;
  }

  @Action({ commit: "TOGGLE_WEAK" })
  public ToggleColorWeak(weakFlag: boolean) {
    return weakFlag;
  }

  @Action({ commit: "TOGGLE_FIXED_SIDEBAR" })
  public ToggleFixSiderbar(fixSiderbar: boolean) {
    return fixSiderbar;
  }

  @Action
  public ToggleFixedHeader(fixedHeader: boolean) {
    if (!fixedHeader) {
      this.context.commit("TOGGLE_FIXED_HEADER_HIDDEN", false);
    }
    this.context.commit("TOGGLE_FIXED_HEADER", fixedHeader);
  }

  @Action
  public ToggleLanguage(lan: string) {
    this.context.commit("APP_LANGUAGE", lan);
  }
}
export const AppModule = getModule(App);
