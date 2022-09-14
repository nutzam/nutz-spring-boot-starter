/* eslint-env node */
require('@rushstack/eslint-patch/modern-module-resolution');

module.exports = {
  root: true,
  extends: [
    'plugin:vue/vue3-recommended',
    'eslint:recommended',
    '@vue/eslint-config-typescript/recommended',
    '@vue/eslint-config-prettier',
    // unplugin-auto-import :: generated automatically
    './.eslintrc-auto-import.json',
  ],
  env: {
    'vue/setup-compiler-macros': true,
  },
  rules: {
    // all rules docs https://eslint.org/docs/rules/
    'prettier/prettier': ['error', { semi: true, singleQuote: true, printWidth: 120 }],
  },
  globals: {
    // 在这里可以添加各种命名空间和全局函数
    acl: true,
    code: true,
  },
};
