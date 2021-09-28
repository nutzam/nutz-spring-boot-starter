import antdEnUS from "ant-design-vue/lib/locale-provider/en_US";
import momentEU from "moment/locale/eu";
import config from "@/core/config";
const components = {
  antLocale: antdEnUS,
  momentName: "eu",
  momentLocale: momentEU,
};

import page from "./enUS/page";

const locale: Record<string, string> = {
  message: "-",
  "acl.user": "User",
  "acl.role": "Role",
  "acl.permission": "Permission",
  acl: "Access Control",
  dictionary: "Codebook",
  dashboard: "Dashboard",

  "usercenter.logout": "Logout",
  usercenter: "UserCenter",
  "usercenter.setting": "Settings",

  "layouts.usermenu.dialog.title": "Message",
  "layouts.usermenu.dialog.content": "Do you really log-out.",

  "app.setting.pagestyle": "Page style setting",
  "app.setting.pagestyle.light": "Light style",
  "app.setting.pagestyle.dark": "Dark style",
  "app.setting.pagestyle.realdark": "RealDark style",
  "app.setting.themecolor": "Theme Color",
  "app.setting.navigationmode": "Navigation Mode",
  "app.setting.content-width": "Content Width",
  "app.setting.fixedheader": "Fixed Header",
  "app.setting.fixedsidebar": "Fixed Sidebar",
  "app.setting.sidemenu": "Side Menu Layout",
  "app.setting.topmenu": "Top Menu Layout",
  "app.setting.content-width.fixed": "Fixed",
  "app.setting.content-width.fluid": "Fluid",
  "app.setting.othersettings": "Other Settings",
  "app.setting.weakmode": "Weak Mode",
  "app.setting.copy": "Copy Setting",
  "app.setting.loading": "Loading theme",
  "app.setting.copyinfo":
    "copy success，please replace defaultSettings in src/models/setting.js",
  "app.setting.production.hint":
    "Setting panel shows in development environment only, please manually modify",

  "app.setting.themecolor.daybreak": "daybreak",
  "app.setting.themecolor.dust": "dust",
  "app.setting.themecolor.volcano": "volcano",
  "app.setting.themecolor.sunset": "sunset",
  "app.setting.themecolor.cyan": "cyan",
  "app.setting.themecolor.green": "green",
  "app.setting.themecolor.geekblue": "geekblue",
  "app.setting.themecolor.purple": "purple",
};

// eslint-disable-next-line
(locale as any)["app.setting.themecolor." + config.primaryColor] = config.primaryColorNameEn;

export default {
  ...components,
  ...locale,
  page,
};
