require('promise.prototype.finally').shim();

import Vue from 'vue';
import App from './App.vue';
import router from './router/router';
import store from './store';
import i18n from './plugins/i18n';
import './plugins';
import './mock';
import validators from '@/utils/validators';
Vue.config.productionTip = false;
Vue.mixin({
  data() {
    return {validators};
  },
});
new Vue({
  router,
  store,
  i18n,
  render: h => h(App),
}).$mount('#app');
