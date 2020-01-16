module.exports = {
  presets: ['@vue/app', '@babel/preset-typescript'],
  plugins: [
    // 按需加载antd组件
    // [
    //   'import',
    //   {libraryName: 'ant-design-vue', libraryDirectory: 'es', style: true},
    // ], // `style: true` 会加载 less 文件
  ],
};
