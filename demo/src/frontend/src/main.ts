import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import Antd, { Icon } from "ant-design-vue";

import vueParticles from "vue-particles";
import ProLayout, { PageHeaderWrapper } from "@ant-design-vue/pro-layout";
import i18n from "./locales";
import "./global.less"; // global style
import "./plugins";
import theme from "@/core/config/theme";
import bootstrap from "./core/index";

Vue.use(Antd);
Vue.use(vueParticles);
Vue.component("ProLayout", ProLayout);
Vue.component("PageContainer", PageHeaderWrapper);
Vue.component("PageHeaderWrapper", PageHeaderWrapper);

Vue.config.productionTip = false;
window.umi_plugin_ant_themeVar = theme.theme;

const Iconfont = Icon.createFromIconfontCN({
  scriptUrl: "//at.alicdn.com/t/font_2754865_21aagocihfp.js", // 在 iconfont.cn 上生成
  extraCommonProps: { class: "icon-font" },
});

Vue.component("Iconfont", Iconfont);
new Vue({
  router,
  store,
  i18n,
  mounted: () => {
    bootstrap();
  },
  render: (h) => h(App),
}).$mount("#app");
