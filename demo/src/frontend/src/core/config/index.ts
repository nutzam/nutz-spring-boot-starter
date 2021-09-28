export default {
  navTheme: "dark", // theme for nav menu
  primaryColor: "#2f7aa1", // primary color of ant design
  primaryColorName: "默认蓝", // primary color of ant design
  primaryColorNameEn: "default blue", // primary color of ant design
  layout: "topmenu", // nav menu position: sidemenu or topmenu
  contentWidth: "Fluid", // layout of content: Fluid or Fixed, only works when layout is topmenu
  fixedHeader: false, // sticky header
  fixSiderbar: false, // sticky siderbar
  colorWeak: false,
  autoHideHeader: false, //  auto hide header
  multiTab: false,
  sideCollapsed: false,
  language: "zh-CN",
  showDrawer: true,
  menu: {
    locale: true,
  },

  name: "ATS",
  title: "ANTDV STARTER",
  description: "基于typescript 开发 ant design vue 应用的一键启动器",
  copyright: "Copyright &copy;  ats.riemann.tech",
  storageOptions: {
    namespace: "ats__", // key prefix
    name: "ls", // name variable Vue.[ls] or this.[$ls],
    storage: "local", // storage name session, local, memory
  },
  http: {
    prefix: process.env.NODE_ENV === "production" ? "/" : "/api",
    timeout: 60 * 1000,
  },
};
