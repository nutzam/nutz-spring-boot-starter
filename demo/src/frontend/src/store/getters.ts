import {IRootState} from '@/store/index';

const getters = {
  sidebar: (state: IRootState) => state.app.sidebar,
  device: (state: IRootState) => state.app.device,
  fixSiderbar: (state: IRootState) => state.app.fixSiderbar,
  layoutMode: (state: IRootState) => state.app.layoutMode,
  navTheme: (state: IRootState) => state.app.navTheme,
  language: (state: IRootState) => state.app.language,
  primaryColor: (state: IRootState) => state.app.primaryColor,
  colorWeak: (state: IRootState) => state.app.colorWeak,
  token: (state: IRootState) => state.user.token,
};

export default getters;
