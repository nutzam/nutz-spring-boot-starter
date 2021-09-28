import Vue, { VNode } from "vue";

declare global {
  interface Window {
    umi_plugin_ant_themeVar: any;
    acl: any;
  }
  namespace JSX {
    // tslint:disable no-empty-interface
    interface Element extends VNode {}
    // tslint:disable no-empty-interface
    interface ElementClass extends Vue {}
    interface IntrinsicElements {
      [elem: string]: any;
    }
  }
}
