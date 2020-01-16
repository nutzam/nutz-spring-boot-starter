module.exports = {
  root: true,

  env: {
    node: true,
  },

  rules: {
    'no-console': 'off',
    'no-debugger': 'off',
  },

  parserOptions: {
    parser: '@typescript-eslint/parser',
  },

  extends: [
    'plugin:vue/strongly-recommended',
    '@vue/prettier',
    '@vue/typescript',
  ],
};
