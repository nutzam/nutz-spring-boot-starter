<template>
  <pro-layout
    v-model:collapsed="state.collapsed"
    v-model:selectedKeys="state.selectedKeys"
    v-model:openKeys="state.openKeys"
    v-model:locale="translate"
    :loading="loading"
    :menu-data="menuData"
    :breadcrumb="{ routes: breadcrumb }"
    disable-content-margin
    style="min-height: 100vh"
    :iconfont-url="config.layout.iconfontUrl"
    v-bind="proConfig"
    @collapse="onCollapse"
  >
    <template #menuHeaderRender>
      <router-link :to="{ path: '/' }">
        <img :src="logo" />
        <h1>{{ useAppStore().title }}</h1>
      </router-link>
    </template>
    <template #rightContentRender>
      <RightContent :current-user="currentUser" />
    </template>
    <!-- custom breadcrumb itemRender  -->
    <template #breadcrumbRender="{ route, params, routes }">
      <span v-if="routes.indexOf(route) === routes.length - 1">
        {{ useAppStore().enableI8n ? $t(route.breadcrumbName) : route.breadcrumbName }}
      </span>
      <router-link v-else-if="routes.indexOf(route) === 0" :to="{ path: route.path, params }">
        <icon-font type="icon-home" />
        {{ useAppStore().enableI8n ? $t(route.breadcrumbName) : route.breadcrumbName }}
      </router-link>
      <router-link v-else :to="{ path: route.path, params }">
        {{ useAppStore().enableI8n ? $t(route.breadcrumbName) : route.breadcrumbName }}
      </router-link>
    </template>
    <SettingDrawer v-model="proConfig" />
    <WaterMark :content="useAppStore().title">
      <RouterView v-slot="{ Component, route }">
        <transition name="slide-left" mode="out-in">
          <component :is="Component" :key="route.path" />
        </transition>
      </RouterView>
    </WaterMark>
    <template #footerRender>
      <GlobalFooter :links="[]" :copyright="useAppStore().copyright"></GlobalFooter>
    </template>
  </pro-layout>
</template>

<script setup lang="ts">
import { useRouter, RouterView, RouterLink } from 'vue-router';
import { translate as translateFN } from '@/locales';

import {
  getMenuData,
  clearMenuItem,
  type RouteContextProps,
  WaterMark,
  GlobalFooter,
} from '@ant-design-vue/pro-layout';
import { IconFont } from '@/components/IconFont/IconFont';
import logo from '@/assets/logo.png';
import { config } from '@/core/settings';
import { useAppStore } from '@/store/app';

const translate = config.enableI8n ? translateFN : (s: string) => s;

const router = useRouter();
const { menuData } = getMenuData(clearMenuItem(router.getRoutes()));
const state = reactive<Omit<RouteContextProps, 'menuData'>>({
  collapsed: useAppStore().layout.collapsed, // default collapsed
  openKeys: [], // default openKeys
  selectedKeys: [], // default selectedKeys
});
const loading = ref(false);
const proConfig = ref({
  collapsedWidth: useAppStore().layout.collapsedWidth,
  contentWidth: useAppStore().layout.contentWidth,
  disableContentMargin: useAppStore().layout.disableContentMargin,
  disableMobile: useAppStore().layout.disableMobile,
  fixSiderbar: useAppStore().layout.fixSiderbar,
  fixedHeader: useAppStore().layout.fixedHeader,
  headerHeight: useAppStore().layout.headerHeight,
  headerTheme: useAppStore().layout.headerTheme,
  layout: useAppStore().layout.layout,
  navTheme: useAppStore().layout.navTheme,
  title: useAppStore().title,
  splitMenus: useAppStore().layout.splitMenus,
  menuHeaderRender: undefined,
  footerRender: undefined,
  headerRender: undefined,
});
const breadcrumb = computed(() => {
  const currentRouteValue = router.currentRoute.value;
  const params = currentRouteValue.params;
  return currentRouteValue.matched.concat().map(item => {
    let path = item.path;
    if (/:/.test(path)) {
      // 在面包屑的 url 中加上正确的路径参数
      const matchedPathParams = path.match(/:[^/]*/g);
      if (matchedPathParams) {
        matchedPathParams.forEach(matchedPathParam => {
          path = path.replace(
            matchedPathParam,
            String(params[matchedPathParam.replace(':', '').replace('?', '')] || ''),
          );
        });
      }
    }
    return {
      path,
      breadcrumbName: item.meta.title || '',
    };
  });
});
const currentUser = reactive({
  nickname: 'Admin',
  avatar: 'A',
});

const onCollapse = (collapsed: boolean): void => {
  useAppStore().collapsedChange(collapsed);
};

watch(
  router.currentRoute,
  () => {
    const matched = router.currentRoute.value.matched.concat();
    state.selectedKeys = matched.filter(r => r.name !== 'index').map(r => r.path);
    state.openKeys = matched.filter(r => r.path !== router.currentRoute.value.path).map(r => r.path);
  },
  {
    immediate: true,
  },
);
</script>
