import Vue from 'vue';
import {
  VuexModule,
  Module,
  Action,
  Mutation,
  getModule,
} from 'vuex-module-decorators';
import * as types from '@/store/mutation-types';
import store from '@/store';
import {DEVICE_TYPE} from '@/utils/device';
import settings from '@/config/defaultSettings';
export interface IAppState {
  device: DEVICE_TYPE;
  navTheme: string;
  primaryColor: string;
  sidebar: boolean;
  layoutMode: string;
  contentWidth: string;
  fixedHeader: boolean;
  fixSiderbar: boolean;
  autoHideHeader: boolean;
  colorWeak: boolean;
  multiTab: boolean;
  language: string;
  title: string;
  description: string;
  uuid: string;
}

@Module({dynamic: true, store, name: 'app'})
class App extends VuexModule implements IAppState {
  public sidebar = true;
  public device = DEVICE_TYPE.DESKTOP;
  public navTheme = 'dark';
  public primaryColor = '';
  public layoutMode = '';
  public contentWidth = '';
  public colorWeak = false;
  public fixedHeader = false;
  public autoHideHeader = false;
  public fixSiderbar = false;
  public multiTab = true;
  public language = 'zhCN';
  public title = settings.title;
  public description = settings.description;
  public copyright = settings.copyright;
  public uuid = '';

  @Mutation
  public SET_SIDEBAR(type: boolean) {
    Vue.ls.set(types.DEFAULT_SIDEBAR_TYPE, type);
    this.sidebar = type;
  }

  @Mutation
  public TOGGLE_NAV_THEME(theme: string) {
    Vue.ls.set(types.DEFAULT_NAV_THEME, theme);
    this.navTheme = theme;
  }

  @Mutation
  public TOGGLE_PRIMARY_COLOR(color: string) {
    Vue.ls.set(types.DEFAULT_PRIMARY_COLOR, color);
    this.primaryColor = color;
  }

  @Mutation
  public CLOSE_SIDEBAR() {
    Vue.ls.set(types.DEFAULT_SIDEBAR_TYPE, false);
    this.sidebar = false;
  }

  @Mutation
  public refresh() {
    this.uuid = Math.random().toString();
  }

  @Mutation
  public TOGGLE_DEVICE(device: DEVICE_TYPE) {
    this.device = device;
  }

  @Mutation
  public TOGGLE_LAYOUT_MODE(layoutMode: string) {
    Vue.ls.set(types.DEFAULT_LAYOUT_MODE, layoutMode);
    this.layoutMode = layoutMode;
  }

  @Mutation
  public TOGGLE_CONTENT_WIDTH(contentWidth: string) {
    Vue.ls.set(types.DEFAULT_CONTENT_WIDTH_TYPE, contentWidth);
    this.contentWidth = contentWidth;
  }

  @Mutation
  public TOGGLE_FIXED_HEADER(fixed: boolean) {
    Vue.ls.set(types.DEFAULT_FIXED_HEADER, fixed);
    this.fixedHeader = fixed;
  }

  @Mutation
  public TOGGLE_FIXED_HEADER_HIDDEN(show: boolean) {
    Vue.ls.set(types.DEFAULT_FIXED_HEADER_HIDDEN, show);
    this.autoHideHeader = show;
  }

  @Mutation
  public TOGGLE_FIXED_SIDERBAR(fixSiderbar: boolean) {
    Vue.ls.set(types.DEFAULT_FIXED_SIDEMENU, fixSiderbar);
    this.fixSiderbar = fixSiderbar;
  }
  @Mutation
  public TOGGLE_MULTI_TAB(payload: boolean) {
    Vue.ls.set(types.DEFAULT_MULTI_TAB, payload);
    this.multiTab = payload;
  }

  @Mutation
  public TOGGLE_COLOR_WEAK(payload: boolean) {
    Vue.ls.set(types.DEFAULT_COLOR_WEAK, payload);
    this.colorWeak = payload;
  }

  @Mutation
  public TOGGLE_LANGUAGE(lan: string) {
    Vue.ls.set(types.DEFAULT_LANGUAGE, lan);
    this.language = lan;
  }

  @Action({commit: 'SET_SIDEBAR'})
  public SetSidebar(type: boolean) {
    return type;
  }

  @Action
  public CloseSidebar() {
    this.context.commit('CLOSE_SIDEBAR');
  }

  @Action({commit: 'TOGGLE_NAV_THEME'})
  public ToggleNavTheme(theme: string) {
    return theme;
  }

  @Action({commit: 'TOGGLE_PRIMARY_COLOR'})
  public TogglePrimaryColor(color: string) {
    return color;
  }

  @Action({commit: 'TOGGLE_CONTENT_WIDTH'})
  public ToggleContentWidth(contentWidth: string) {
    return contentWidth;
  }

  @Action({commit: 'TOGGLE_DEVICE'})
  public ToggleDevice(device: DEVICE_TYPE) {
    return device;
  }

  @Action({commit: 'TOGGLE_LAYOUT_MODE'})
  public ToggleLayoutMode(layoutMode: string) {
    return layoutMode;
  }

  @Action({commit: 'TOGGLE_MULTI_TAB'})
  public ToggleMultiTab(payload: boolean) {
    return payload;
  }

  @Action({commit: 'TOGGLE_FIXED_HEADER_HIDDEN'})
  public ToggleFixedHeaderHidden(show: boolean) {
    return show;
  }

  @Action({commit: 'TOGGLE_COLOR_WEAK'})
  public ToggleColorWeak(weakFlag: boolean) {
    return weakFlag;
  }

  @Action({commit: 'TOGGLE_FIXED_SIDERBAR'})
  public ToggleFixSiderbar(fixSiderbar: boolean) {
    return fixSiderbar;
  }

  @Action
  public ToggleFixedHeader(fixedHeader: boolean) {
    if (!fixedHeader) {
      this.context.commit('TOGGLE_FIXED_HEADER_HIDDEN', false);
    }
    this.context.commit('TOGGLE_FIXED_HEADER', fixedHeader);
  }

  @Action({commit: 'TOGGLE_LANGUAGE'})
  public ToggleLanguage(lan: string) {
    return lan;
  }
}

export const AppModule = getModule(App);
