module.exports = {
  root: true,

  env: {
    node: true,
  },

  extends: [
    "plugin:vue/strongly-recommended",
    "@vue/typescript/recommended",
    "@vue/prettier",
    "@vue/prettier/@typescript-eslint",
  ],

  parserOptions: {
    ecmaVersion: 2020,
  },

  rules: {
    "no-console": "off",
    "no-debugger": "off",
  },
};
