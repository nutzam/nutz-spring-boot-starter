import Vue from 'vue';
import 'xe-utils';
import VXETable from 'vxe-table';
import VXETablePluginAntd from 'vxe-table-plugin-antd';
import 'vxe-table/lib/index.css';
import 'vxe-table-plugin-antd/dist/style.css';
import i18n from './i18n';
Vue.use(VXETable, {i18n: (key: any, value: any) => i18n.t(key, value)});
VXETable.use(VXETablePluginAntd);
