<template>
  <pro-layout
    :menus="menus"
    :collapsed="collapsed"
    :media-query="query"
    :is-mobile="isMobile"
    :handle-media-query="handleMediaQuery"
    :handle-collapse="handleCollapse"
    :i18n-render="i18nRender"
    v-bind="settings"
  >
    <setting-drawer
      :settings="settings"
      @change="handleSettingChange"
      :lang="AppModule.lang"
      v-if="AppModule.showDrawer"
    />
    <template #menuHeaderRender>
      <span>
        <logo-svg />
        <h1>{{ AppModule.title.substr(0, 12) }}</h1>
      </span>
    </template>
    <template #rightContentRender>
      <right-content
        :top-menu="settings.layout === 'topmenu'"
        :is-mobile="isMobile"
        :theme="settings.theme"
      />
    </template>
    <template #footerRender>
      <global-footer />
    </template>
    <router-view />
    <div class="uuid_no_show">{{ AppModule.uuid }}</div>
  </pro-layout>
</template>
<script lang="ts">
import { Component, Mixins } from "vue-property-decorator";
import { SettingDrawer } from "@ant-design-vue/pro-layout";
import { i18nRender } from "@/locales";

import RightContent from "@/components/GlobalHeader/RightContent.vue";
import GlobalFooter from "@/components/GlobalFooter/index.vue";
import LogoSvg from "@/assets/logo.svg?inline";
import { Mixin } from "@/utils/mixin";
import { AppModule } from "../store/modules/app";
import { CONTENT_WIDTH_TYPE } from "@/store/mutation-types";
import { dynamicFilteredMenus } from "@/utils/auth";
import { RouteConfig } from "vue-router";

@Component({
  components: { SettingDrawer, RightContent, GlobalFooter, LogoSvg },
})
export default class BasicLayout extends Mixins(Mixin) {
  menus: Array<unknown> | undefined = undefined;
  collapsed = true;
  settings = {
    layout: AppModule.layout,
    contentWidth:
      AppModule.layout === "sidemenu"
        ? CONTENT_WIDTH_TYPE.Fluid
        : AppModule.contentWidth,
    theme: AppModule.theme,
    primaryColor: AppModule.color,
    fixedHeader: AppModule.fixedHeader,
    fixSiderbar: AppModule.fixedSidebar,
    colorWeak: AppModule.weak,
    hideHintAlert: true,
    hideCopyButton: true,
  };
  query = {};
  isMobile = false;
  i18nRender = i18nRender;
  created(): void {
    const routers = this.$router.options && this.$router.options.routes;
    const menu =
      routers == undefined
        ? undefined
        : routers.find((route) => route.path === "/index");
    this.menus = this.getMenuData(menu == undefined ? [] : menu.children);
    this.$watch("collapsed", () => {
      AppModule.SIDEBAR_TYPE(this.collapsed);
    });
    this.$watch("isMobile", () => {
      AppModule.TOGGLE_MOBILE_TYPE(this.isMobile);
    });
  }

  getMenuData(routes: Array<RouteConfig> = []): Array<unknown> | undefined {
    return dynamicFilteredMenus(routes);
  }

  mounted(): void {
    const userAgent = navigator.userAgent;
    if (userAgent.indexOf("Edge") > -1) {
      this.$nextTick(() => {
        this.collapsed = !this.collapsed;
        setTimeout(() => {
          this.collapsed = !this.collapsed;
        }, 16);
      });
    }
  }

  handleMediaQuery(val: { "screen-xs"?: string }): void {
    this.query = val;
    if (this.isMobile && !val["screen-xs"]) {
      this.isMobile = false;
      return;
    }
    if (!this.isMobile && val["screen-xs"]) {
      this.isMobile = true;
      this.collapsed = false;
      this.settings.contentWidth = CONTENT_WIDTH_TYPE.Fluid;
    }
  }

  handleCollapse(val: boolean): void {
    this.collapsed = val;
  }

  handleSettingChange({
    type,
    value,
  }: {
    type: string;
    value: boolean | string;
  }): void {
    switch (type) {
      case "theme":
        AppModule.ToggleNavTheme(String(value));
        this.settings.theme = String(value);
        break;
      case "primaryColor":
        AppModule.TogglePrimaryColor(String(value));
        this.settings.primaryColor = String(value);
        break;
      case "contentWidth":
        AppModule.ToggleContentWidth(String(value));
        this.settings[type] = value;
        break;
      case "fixedHeader":
        AppModule.ToggleFixedHeader(Boolean(value));
        this.settings[type] = value;
        break;
      case "fixSiderbar":
        AppModule.ToggleFixSiderbar(Boolean(value));
        this.settings[type] = value;
        break;
      case "colorWeak":
        AppModule.ToggleColorWeak(Boolean(value));
        this.settings[type] = value;
        break;
      case "layout":
        AppModule.ToggleLayoutMode(String(value));
        this.settings.layout = String(value);
        if (value === "sidemenu") {
          this.settings.contentWidth = CONTENT_WIDTH_TYPE.Fluid;
        } else {
          this.settings.fixSiderbar = false;
          this.settings.contentWidth = CONTENT_WIDTH_TYPE.Fixed;
        }
        break;
      default:
        break;
    }
    AppModule.refresh();
  }
}
</script>
<style lang="less">
@import "./BasicLayout.less";
</style>
