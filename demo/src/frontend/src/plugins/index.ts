import Vue from 'vue';
import Storage from 'vue-ls';

import config from '@/config/defaultSettings';
import Authorized from '@/components/Authorized';
import Auth from '@/directives/auth';
import './ant-design-vue';
import './axios';
import '@/utils/filter'; // global filter
import VueClipboard from 'vue-clipboard2';
import './vux-table';
Vue.use(VueClipboard);
import Viser from 'viser-vue';
Vue.use(Viser);
import {message, notification} from 'ant-design-vue';
import {ApiPlugin} from '@/api/index';
// base library

Vue.config.productionTip = false;
Vue.prototype.$message = message;
Vue.prototype.$notify = notification;

// 全局权限组件
Vue.component('Authorized', Authorized);
Vue.use(Auth);
Vue.use(ApiPlugin);
Vue.use(Storage, config.storageOptions);
