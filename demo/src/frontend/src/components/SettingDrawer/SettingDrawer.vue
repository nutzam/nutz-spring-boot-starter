<template>
  <div class="setting-drawer" ref="settingDrawer">
    <a-drawer
      width="300px"
      placement="right"
      :mask-closable="false"
      :closable="false"
      :visible="visible"
      :get-container="() => $refs.settingDrawer"
      @close="onClose"
    >
      <template #handle>
        <div class="setting-drawer-handle" @click="visible = !visible">
          <a-icon :type="visible ? 'close' : 'setting'" />
        </div>
      </template>
      <div class="setting-drawer-index-content">
        <div style="margin-bottom: 24px">
          <h3 class="setting-drawer-index-title">
            {{ i18nRender("global.settingDrawer.pageStyleTitle") }}
          </h3>
          <div class="setting-drawer-index-blockChecbox">
            <a-tooltip>
              <template slot="title">
                {{ i18nRender("global.settingDrawer.dartTooltipTitle") }}
              </template>
              <div
                class="setting-drawer-index-item"
                @click="handleChangeTheme('dark')"
              >
                <img
                  src="https://gw.alipayobjects.com/zos/rmsportal/LCkqqYNmvBEbokSDscrm.svg"
                  alt="dark"
                />
                <div
                  class="setting-drawer-index-selectIcon"
                  v-if="navTheme === 'dark'"
                >
                  <a-icon type="check" />
                </div>
              </div>
            </a-tooltip>
            <a-tooltip>
              <template slot="title">
                {{ i18nRender("global.settingDrawer.lightTooltipTitle") }}
              </template>
              <div
                class="setting-drawer-index-item"
                @click="handleChangeTheme('light')"
              >
                <img
                  src="https://gw.alipayobjects.com/zos/rmsportal/jpRkZQMyYRryryPNtyIC.svg"
                  alt="light"
                />
                <div
                  class="setting-drawer-index-selectIcon"
                  v-if="navTheme !== 'dark'"
                >
                  <a-icon type="check" />
                </div>
              </div>
            </a-tooltip>
          </div>
        </div>

        <div style="margin-bottom: '24px'">
          <h3 class="setting-drawer-index-title">
            {{ i18nRender("global.settingDrawer.themeTitle") }}
          </h3>
          <div style="height: 20px">
            <a-tooltip
              class="setting-drawer-theme-color-colorBlock"
              v-for="(item, index) in colorList"
              :key="index"
            >
              <template slot="title">{{
                i18nRender(`global.settingDrawer.${item.key}`)
              }}</template>
              <a-tag :color="item.color" @click="handleChangeColor(item.color)">
                <a-icon
                  type="check"
                  v-if="item.color === primaryColor"
                ></a-icon>
              </a-tag>
            </a-tooltip>
          </div>
        </div>
        <a-divider />
        <div style="margin-bottom: 24px">
          <h3 class="setting-drawer-index-title">
            {{ i18nRender("global.settingDrawer.layoutTitle") }}
          </h3>
          <div class="setting-drawer-index-blockChecbox">
            <a-tooltip>
              <template slot="title">{{
                i18nRender("global.settingDrawer.sideMenuTitle")
              }}</template>
              <div
                class="setting-drawer-index-item"
                @click="handleChangeLayout('sidemenu')"
              >
                <img
                  src="https://gw.alipayobjects.com/zos/rmsportal/JopDzEhOqwOjeNTXkoje.svg"
                  alt="sidemenu"
                />
                <div
                  class="setting-drawer-index-selectIcon"
                  v-if="layoutMode === 'sidemenu'"
                >
                  <a-icon type="check" />
                </div>
              </div>
            </a-tooltip>
            <a-tooltip>
              <template slot="title">{{
                i18nRender("global.settingDrawer.topMenuTitle")
              }}</template>
              <div
                class="setting-drawer-index-item"
                @click="handleChangeLayout('topmenu')"
              >
                <img
                  src="https://gw.alipayobjects.com/zos/rmsportal/KDNDBbriJhLwuqMoxcAr.svg"
                  alt="topmenu"
                />
                <div
                  class="setting-drawer-index-selectIcon"
                  v-if="layoutMode !== 'sidemenu'"
                >
                  <a-icon type="check" />
                </div>
              </div>
            </a-tooltip>
          </div>

          <div :style="{ marginTop: '24px' }">
            <a-list :split="false">
              <a-list-item>
                <a-tooltip slot="actions">
                  <a-select
                    size="small"
                    style="width: 80px"
                    :default-value="AppModule.contentWidth"
                    @change="handleContentWidthChange"
                  >
                    <a-select-option value="Fixed">{{
                      i18nRender("global.settingDrawer.fixed")
                    }}</a-select-option>

                    <a-select-option
                      value="Fluid"
                      v-if="layoutMode !== 'sidemenu'"
                      >{{
                        i18nRender("global.settingDrawer.flux")
                      }}</a-select-option
                    >
                  </a-select>
                </a-tooltip>
                <a-list-item-meta>
                  <div slot="title">
                    {{ i18nRender("global.settingDrawer.contentWidth") }}
                  </div>
                </a-list-item-meta>
              </a-list-item>

              <a-list-item>
                <a-switch
                  slot="actions"
                  size="small"
                  :default-checked="AppModule.fixedHeader"
                  @change="handleFixedHeader"
                />
                <a-list-item-meta>
                  <div slot="title">
                    {{ i18nRender("global.settingDrawer.fixHeader") }}
                  </div>
                </a-list-item-meta>
              </a-list-item>
              <a-list-item>
                <a-switch
                  slot="actions"
                  size="small"
                  :disabled="!AppModule.fixedHeader"
                  :default-checked="AppModule.autoHideHeader"
                  @change="handleFixedHeaderHidden"
                />
                <a-list-item-meta>
                  <a-tooltip slot="title" placement="left">
                    <template slot="title">{{
                      i18nRender("global.settingDrawer.effectiveWhenFixHeader")
                    }}</template>
                    <div
                      :style="{ opacity: !AppModule.fixedHeader ? '0.5' : '1' }"
                    >
                      {{
                        i18nRender("global.settingDrawer.hideHeaderWhenSliding")
                      }}
                    </div>
                  </a-tooltip>
                </a-list-item-meta>
              </a-list-item>
              <a-list-item>
                <a-switch
                  slot="actions"
                  size="small"
                  :disabled="layoutMode === 'topmenu'"
                  :default-checked="AppModule.fixSiderbar"
                  @change="handleFixSiderbar"
                />
                <a-list-item-meta>
                  <div
                    slot="title"
                    :style="{
                      textDecoration:
                        layoutMode === 'topmenu' ? 'line-through' : 'unset',
                    }"
                  >
                    {{ i18nRender("global.settingDrawer.fixedSideMenu") }}
                  </div>
                </a-list-item-meta>
              </a-list-item>
            </a-list>
          </div>
        </div>
        <a-divider />

        <div :style="{ marginBottom: '24px' }">
          <h3 class="setting-drawer-index-title">
            {{ i18nRender("global.settingDrawer.other") }}
          </h3>
          <div>
            <a-list :split="false">
              <a-list-item>
                <a-switch
                  slot="actions"
                  size="small"
                  :default-checked="AppModule.colorWeak"
                  @change="onColorWeak"
                />
                <a-list-item-meta>
                  <div slot="title">
                    {{ i18nRender("global.settingDrawer.colorWeakMode") }}
                  </div>
                </a-list-item-meta>
              </a-list-item>
              <a-list-item>
                <a-switch
                  slot="actions"
                  size="small"
                  :default-checked="AppModule.multiTab"
                  @change="onMultiTab"
                />
                <a-list-item-meta>
                  <div slot="title">
                    {{ i18nRender("global.settingDrawer.multiTabMode") }}
                  </div>
                </a-list-item-meta>
              </a-list-item>
            </a-list>
          </div>
        </div>
        <a-divider />
      </div>
    </a-drawer>
  </div>
</template>

<script lang="ts">
/* eslint-disable @typescript-eslint/no-explicit-any */

import { Component, Mixins } from "vue-property-decorator";
import { Getter, Action } from "vuex-class";
import { updateTheme } from "@ant-design-vue/pro-layout";
import { colorList, updateColorWeak } from "@/core";
import { Mixin } from "@/utils/mixin";
import { i18nRender } from "@/locales";
interface ColorList {
  key: string;
  color: string;
}
@Component
export default class SettingDrawer extends Mixins(Mixin) {
  private visible = false;
  private colorList: Array<ColorList> = colorList;
  @Getter layoutMode: any;
  @Getter navTheme: any;
  @Getter primaryColor: any;
  @Getter colorWeak?: boolean;
  i18nRender = i18nRender;
  @Action("ToggleLayoutMode") toggleLayoutMode: any;
  @Action("ToggleNavTheme") toggleNavTheme: any;
  @Action("TogglePrimaryColor") togglePrimaryColor: any;

  private onClose(): void {
    this.visible = false;
  }
  private handleChangeTheme(value: string): void {
    this.toggleNavTheme(value);
  }
  private handleChangeColor(color: string): void {
    if (this.primaryColor !== color) {
      this.togglePrimaryColor(color);
      updateTheme(
        true,
        color,
        this.i18nRender(`global.header.compilingTheme`).toString()
      );
    }
  }
  private handleChangeLayout(layout: string): void {
    this.toggleLayoutMode(layout);
    this.handleFixSiderbar(false);
  }
  handleContentWidthChange(type: string): void {
    this.$store.dispatch("ToggleContentWidth", type);
  }
  changeColor(color: string): void {
    if (this.primaryColor !== color) {
      this.$store.dispatch("ToggleColor", color);
      updateTheme(color);
    }
  }
  handleFixedHeader(fixed: boolean): void {
    this.$store.dispatch("ToggleFixedHeader", fixed);
  }
  handleFixedHeaderHidden(autoHidden: boolean): void {
    this.$store.dispatch("ToggleFixedHeaderHidden", autoHidden);
  }
  handleFixSiderbar(fixed: boolean): void {
    if (this.AppModule.layout === "topmenu") {
      this.$store.dispatch("ToggleFixSiderbar", false);
      return;
    }
    this.$store.dispatch("ToggleFixSiderbar", fixed);
  }
  onColorWeak(checked: boolean): void {
    this.$store.dispatch("ToggleColorWeak", checked);
    updateColorWeak(checked);
  }
  onMultiTab(checked: boolean): void {
    this.$store.dispatch("ToggleMultiTab", checked);
  }
}
</script>
<style lang="less" src="./index.less" />
