<template>
  <a-dropdown placement="bottomRight">
    <span :class="prefixCls">
      <a-icon type="global" :title="i18nRender('navBar.lang')" />
    </span>
    <a-menu
      slot="overlay"
      class="menu ant-pro-header-menu"
      :selected-keys="[currentLang]"
      @click="changeLang"
    >
      <a-menu-item v-for="locale in locales" :key="locale">
        <span role="img" :aria-label="languageLabels[locale]">
          {{ languageIcons[locale] }}
        </span>
        {{ " " }} {{ languageLabels[locale] }}
      </a-menu-item>
    </a-menu>
  </a-dropdown>
</template>
<script lang="ts">
import { Component, Mixins, Prop } from "vue-property-decorator";
import { i18nRender } from "@/locales";
import { I18nMixin } from "@/utils/mixin";
@Component({
  components: {},
})
export default class SelectLang extends Mixins(I18nMixin) {
  @Prop({ type: String, default: "ant-pro-drop-down" })
  private prefixCls!: string;

  locales = ["zh-CN", "en-US"];
  languageLabels = {
    "zh-CN": "简体中文",
    "zh-TW": "中文繁体",
    "en-US": "English",
  };
  languageIcons = {
    "zh-CN": "🇨🇳",
    "zh-TW": "🇭🇰",
    "en-US": "🇺🇸",
  };
  i18nRender = i18nRender;
  changeLang({ key }: { key: string }): void {
    this.setLang(key);
  }
}
</script>
<style lang="less" scoped>
@import "./index.less";
</style>
