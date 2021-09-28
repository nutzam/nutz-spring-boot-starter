/* eslint-disable @typescript-eslint/no-explicit-any */
import Vue from "vue";
import "xe-utils";
import VXETable from "vxe-table";
import VXETablePluginAntd from "vxe-table-plugin-antd";
import "vxe-table/lib/index.css";
import "vxe-table-plugin-antd/dist/style.css";
import VXETablePluginExportXLSX from "vxe-table-plugin-export-xlsx";

import i18n from "@/locales";
Vue.use(VXETable, { i18n: (key: any, value: any) => i18n.t(key, value) });
VXETable.use(VXETablePluginAntd);
VXETable.use(VXETablePluginExportXLSX);
