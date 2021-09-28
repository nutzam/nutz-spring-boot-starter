/* eslint-disable @typescript-eslint/no-var-requires */
/* jshint esversion: 6 */

const ThemeColorReplacer = require("webpack-theme-color-replacer");
const generate = require("@ant-design/colors").generate;
const getAntdSerials = (color) => {
  // 淡化（即less的tint）
  const lightens = new Array(9).fill(undefined).map((t, i) => {
    return ThemeColorReplacer.varyColor.lighten(color, i / 10);
  });
  const colorPalettes = generate(color);
  const rgb = ThemeColorReplacer.varyColor
    .toNum3(color.replace("#", ""))
    .join(",");
  return lightens.concat(colorPalettes).concat(rgb);
};

const themePluginOption = {
  fileName: "css/theme-colors-[contenthash:8].css",
  matchColors: getAntdSerials("#1890ff"), // 主色系列
  // 改变样式选择器，解决样式覆盖问题
  changeSelector(selector) {
    switch (selector) {
      case ".ant-calendar-today .ant-calendar-date":
        return (
          ":not(.ant-calendar-selected-date):not(.ant-calendar-selected-day)" +
          selector
        );
      case ".ant-btn:focus,.ant-btn:hover":
        return ".ant-btn:focus:not(.ant-btn-primary):not(.ant-btn-danger),.ant-btn:hover:not(.ant-btn-primary):not(.ant-btn-danger)";
      case ".ant-btn.active,.ant-btn:active":
        return ".ant-btn.active:not(.ant-btn-primary):not(.ant-btn-danger),.ant-btn:active:not(.ant-btn-primary):not(.ant-btn-danger)";
      case ".ant-steps-item-process .ant-steps-item-icon > .ant-steps-icon":
      case ".ant-steps-item-process .ant-steps-item-icon>.ant-steps-icon":
        return ":not(.ant-steps-item-process)" + selector;
      case ".ant-menu-horizontal>.ant-menu-item-active,.ant-menu-horizontal>.ant-menu-item-open,.ant-menu-horizontal>.ant-menu-item-selected,.ant-menu-horizontal>.ant-menu-item:hover,.ant-menu-horizontal>.ant-menu-submenu-active,.ant-menu-horizontal>.ant-menu-submenu-open,.ant-menu-horizontal>.ant-menu-submenu-selected,.ant-menu-horizontal>.ant-menu-submenu:hover":
      case ".ant-menu-horizontal > .ant-menu-item-active,.ant-menu-horizontal > .ant-menu-item-open,.ant-menu-horizontal > .ant-menu-item-selected,.ant-menu-horizontal > .ant-menu-item:hover,.ant-menu-horizontal > .ant-menu-submenu-active,.ant-menu-horizontal > .ant-menu-submenu-open,.ant-menu-horizontal > .ant-menu-submenu-selected,.ant-menu-horizontal > .ant-menu-submenu:hover":
        return ".ant-menu-horizontal > .ant-menu-item-active,.ant-menu-horizontal > .ant-menu-item-open,.ant-menu-horizontal > .ant-menu-item-selected,.ant-menu-horizontal:not(.ant-menu-dark) > .ant-menu-item:hover,.ant-menu-horizontal > .ant-menu-submenu-active,.ant-menu-horizontal > .ant-menu-submenu-open,.ant-menu-horizontal:not(.ant-menu-dark) > .ant-menu-submenu-selected,.ant-menu-horizontal:not(.ant-menu-dark) > .ant-menu-submenu:hover";
      case ".ant-menu-horizontal > .ant-menu-item-selected > a":
      case ".ant-menu-horizontal>.ant-menu-item-selected>a":
        return ".ant-menu-horizontal:not(ant-menu-light):not(.ant-menu-dark) > .ant-menu-item-selected > a";
      case ".ant-menu-horizontal > .ant-menu-item > a:hover":
      case ".ant-menu-horizontal>.ant-menu-item>a:hover":
        return ".ant-menu-horizontal:not(ant-menu-light):not(.ant-menu-dark) > .ant-menu-item > a:hover";
      default:
        return selector;
    }
  },
};
module.exports = {
  publicPath: "./",
  css: {
    loaderOptions: {
      less: {
        lessOptions: {
          modifyVars: {
            // less vars，customize ant design theme

            // 'link-color': '#F5222D',
            "border-radius-base": "2px",
          },
          javascriptEnabled: true,
        },
        // 解决less.bezierEasingMixin();问题
      },
    },
  },
  configureWebpack: {
    plugins: [new ThemeColorReplacer(themePluginOption)],
  },
  chainWebpack: (config) => {
    const svgRule = config.module.rule("svg");
    svgRule.uses.clear();
    svgRule
      .oneOf("inline")
      .resourceQuery(/inline/)
      .use("vue-svg-icon-loader")
      .loader("vue-svg-icon-loader")
      .end()
      .end()
      .oneOf("external")
      .use("file-loader")
      .loader("file-loader")
      .options({
        name: "assets/[name].[hash:8].[ext]",
      });
  },
  devServer: {
    disableHostCheck: true,
    port: process.env.PORT || 8080,
    https: false,
    hotOnly: false,
    proxy: {
      api: {
        target: "http://127.0.0.1:8888",
        changeOrigin: true,
        pathRewrite: {
          "^/api": "",
        },
      },
    },
  },
};
