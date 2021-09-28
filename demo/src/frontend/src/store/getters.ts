import { RootState } from "@/store/index";
import config from "@/core/config";

const getters = {
  sidebar: (state: RootState): boolean => state.app.fixedSidebar,
  // device: (state: IRootState) => state.app.device,
  fixSiderbar: (state: RootState): boolean => state.app.fixedSidebar,
  layoutMode: (state: RootState): string => state.app.layout,
  navTheme: (state: RootState): string => state.app.theme,
  language: (state: RootState): string => state.app.lang || config.language,
  primaryColor: (state: RootState): string => state.app.color,
  colorWeak: (state: RootState): boolean => state.app.weak,
  token: (state: RootState): string => state.user.token,
};

export default getters;
