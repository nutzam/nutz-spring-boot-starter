import * as types from "@/store/mutation-types";
import { AppModule } from "@/store/modules/app";
import { UserModule } from "@/store/modules/user";
import config from "@/core/config";
import storage from "store";
import { updateTheme } from "@ant-design-vue/pro-layout";

export const colorList = [
  {
    key: "dustRed",
    color: "#F5222D",
  },
  {
    key: "volcano",
    color: "#FA541C",
  },
  {
    key: "sunsetOrange",
    color: "#FAAD14",
  },
  {
    key: "cyan",
    color: "#13C2C2",
  },
  {
    key: "polarGreen",
    color: "#52C41A",
  },
  {
    key: "daybreakBlue",
    color: "#19ACE9",
  },
  {
    key: "geekBlue",
    color: "#2F54EB",
  },
  {
    key: "goldenPurple",
    color: "#722ED1",
  },
];

export default function Initializer(): void {
  updateTheme(
    storage.get(
      types.TOGGLE_COLOR,
      storage.get(types.TOGGLE_COLOR, config.primaryColor)
    )
  );
  AppModule.SIDEBAR_TYPE(storage.get(types.SIDEBAR_TYPE, true));
  AppModule.TOGGLE_COLOR(storage.get(types.TOGGLE_COLOR, config.primaryColor));
  AppModule.TOGGLE_NAV_THEME(storage.get(types.NAV_THEME, config.navTheme));
  AppModule.TOGGLE_LAYOUT(storage.get(types.TOGGLE_LAYOUT, config.layout));
  AppModule.TOGGLE_FIXED_HEADER(
    storage.get(types.TOGGLE_FIXED_HEADER, config.fixedHeader)
  );
  AppModule.TOGGLE_FIXED_SIDEBAR(
    storage.get(types.TOGGLE_FIXED_SIDEBAR, config.autoHideHeader)
  );
  AppModule.TOGGLE_CONTENT_WIDTH(
    storage.get(types.CONTENT_WIDTH_TYPE, config.contentWidth)
  );
  AppModule.TOGGLE_HIDE_HEADER(
    storage.get(types.TOGGLE_HIDE_HEADER, config.autoHideHeader)
  );
  AppModule.TOGGLE_WEAK(storage.get(types.TOGGLE_WEAK, config.colorWeak));
  AppModule.TOGGLE_MULTI_TAB(
    storage.get(types.TOGGLE_MULTI_TAB, config.multiTab)
  );
  AppModule.APP_LANGUAGE(storage.get(types.APP_LANGUAGE, config.language));
  UserModule.login(storage.get(types.LOGIN_USER, {}));
}

export const updateColorWeak = (colorWeak: boolean): void => {
  colorWeak
    ? document.body.classList.add("colorWeak")
    : document.body.classList.remove("colorWeak");
};
