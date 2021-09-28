import antd from "ant-design-vue/lib/locale-provider/zh_CN";
import momentCN from "moment/locale/zh-cn";
import config from "@/core/config";

const components = {
  antLocale: antd,
  momentName: "zh-cn",
  momentLocale: momentCN,
};
import page from "./zhCN/page";
const locale: Record<string, string> = {
  message: "-",
  "acl.user": "用户管理",
  "acl.role": "角色管理",
  "acl.permission": "权限管理",
  acl: "访问控制",
  dictionary: "数据字典",
  dashboard: "仪表盘",

  "usercenter.logout": "退出登录",
  usercenter: "用户中心",
  "usercenter.setting": "个人设置",

  "layouts.usermenu.dialog.title": "提示",
  "layouts.usermenu.dialog.content": "您确定要退出登录?",

  "app.setting.pagestyle": "页面样式",
  "app.setting.pagestyle.light": "明亮模式",
  "app.setting.pagestyle.dark": "黑暗模式",
  "app.setting.pagestyle.realdark": "纯黑模式",
  "app.setting.themecolor": "主题色",
  "app.setting.navigationmode": "导航模式",
  "app.setting.content-width": "内容宽度",
  "app.setting.fixedheader": "固定头部",
  "app.setting.fixedsidebar": "固定侧栏",
  "app.setting.sidemenu": "侧面菜单",
  "app.setting.topmenu": "顶部菜单",
  "app.setting.content-width.fixed": "固定",
  "app.setting.content-width.fluid": "流式",
  "app.setting.othersettings": "其他设置",
  "app.setting.weakmode": "色弱模式",
  "app.setting.copy": "复制设置",
  "app.setting.loading": "正在加载主题",
  "app.setting.copyinfo": "复制成功，请替换 src/models/setting.js 中的设置内容",
  "app.setting.production.hint": "设置面板仅在开发环境显示,请自行修改",
  "app.setting.fixedsidebar.hint": "固定侧栏",

  "app.setting.themecolor.daybreak": "拂晓蓝",
  "app.setting.themecolor.dust": "薄暮",
  "app.setting.themecolor.volcano": "火山",
  "app.setting.themecolor.sunset": "日暮",
  "app.setting.themecolor.cyan": "明青",
  "app.setting.themecolor.green": "极光绿",
  "app.setting.themecolor.geekblue": "极客蓝",
  "app.setting.themecolor.purple": "酱紫",

  "global.http.error": "错误",
  "global.http.forbidden": "没有权限",
  "global.http.notFount": "资源不存在",
  "global.http.configError": "配置错误",
};
// eslint-disable-next-line
(locale as any)["app.setting.themecolor." + config.primaryColor] = config.primaryColorName;

export default {
  ...components,
  ...locale,
  page,
};
