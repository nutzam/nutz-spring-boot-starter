/*jshint esversion: 6 */
const path = require('path');
const webpack = require('webpack');
const AntDesignThemePlugin = require('antd-theme-webpack-plugin');

// 主题切换配置 生成color.less
// https://github.com/mzohaibqc/antd-theme-webpack-plugin/blob/master/index.js
let options = {
  antDir: path.join(__dirname, './node_modules/ant-design-vue'),
  stylesDir: path.join(__dirname, './src'),
  varFile: path.join(
    __dirname,
    './node_modules/ant-design-vue/lib/style/themes/default.less'
  ),
  mainLessFile: '',
  themeVariables: ['@primary-color'],
  publicPath: './',
  generateOnce: false,
};

const themePlugin = new AntDesignThemePlugin(options);

const port = 8080; // dev port

module.exports = {
  productionSourceMap: false,
  publicPath: './',
  css: {
    loaderOptions: {
      less: {
        // 解决less .bezierEasingMixin();问题
        javascriptEnabled: true,
      },
    },
  },
  configureWebpack: {
    // 需要单独在用时引入所需包
    plugins: [themePlugin, new webpack.IgnorePlugin(/^\.\/locale$/, /moment$/)],
  },
  chainWebpack: config => {
    const svgRule = config.module.rule('svg');
    svgRule.uses.clear();
    svgRule
      .oneOf('inline')
      .resourceQuery(/inline/)
      .use('vue-svg-icon-loader')
      .loader('vue-svg-icon-loader')
      .end()
      .end()
      .oneOf('external')
      .use('file-loader')
      .loader('file-loader')
      .options({
        name: 'assets/[name].[hash:8].[ext]',
      });
  },
  devServer: {
    port: port,
    open: true,
    overlay: {
      warnings: false,
      errors: true,
    },
    proxy: {
      api: {
        target: 'http://localhost:8888',
        changeOrigin: true,
        pathRewrite: {
          '^/api': '',
        },
      },
    },
  },
};
