<template>
  <ConfigProvider :locale="locale">
    <router-view />
  </ConfigProvider>
</template>

<script setup lang="ts">
import { ConfigProvider } from 'ant-design-vue';
import { useI18n } from 'vue-i18n';
import { useUserTheme } from './hooks/useTheme';
import { useTitle } from '@vueuse/core';
import { messages } from './locales';
import { useAppStore } from './store/app';
import type { Locale } from 'ant-design-vue/lib/locale-provider';
useI18n().locale.value = useAppStore().language;
useUserTheme();
useTitle(useAppStore().title); //TODO title 可以设置每页的路由meta信息
const locale = computed(() => {
  return messages[useAppStore().language] as unknown as Locale;
});
</script>

<style>
#app {
  height: 100%;
}

.ant-pro-sider {
  z-index: 20;
}

.slide-left-enter-active,
.slide-left-leave-active,
.slide-right-enter-active,
.slide-right-leave-active {
  transition-duration: 0.5s;
  transition-property: height, opacity, transform;
  transition-timing-function: cubic-bezier(0.55, 0, 0.1, 1);
  overflow: hidden;
}

.slide-left-enter,
.slide-right-leave-active {
  opacity: 0;
  transform: translate(2em, 0);
}

.slide-left-leave-active,
.slide-right-enter {
  opacity: 0;
  transform: translate(-2em, 0);
}

.zoom-enter-active,
.zoom-leave-active {
  animation-duration: 0.3s;
  animation-fill-mode: both;
  animation-name: zoomIn;
}

@keyframes zoomIn {
  from {
    opacity: 0;
    transform: scale3d(0.95, 0.95, 0.95);
  }

  100% {
    opacity: 1;
  }
}
@keyframes zoomOut {
  0% {
    opacity: 1;
  }

  to {
    opacity: 0;
    transform: scale3d(0.95, 0.95, 0.95);
  }
}
.ant-pro-global-footer {
  margin: 8px 0 8px 0 !important;
}
</style>
