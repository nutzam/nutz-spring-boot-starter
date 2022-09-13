<template>
  <div style="margin-right: 12px">
    <a-space>
      <a style="padding: 0 12px; display: inline-block; user-select: none" @click="handleClick">
        <a-avatar shape="square" size="small">
          <template #icon>
            <icon-font type="icon-palette" />
          </template>
        </a-avatar>
      </a>
      <a-dropdown>
        <template #overlay>
          <a-menu v-model:selectedKeys="currentLanguage" @click="switchLanguage">
            <a-menu-item key="zh_CN">
              <template #icon>
                <icon-font type="icon-chinese" />
              </template>
              <span>中文-简体</span>
            </a-menu-item>
            <a-menu-item key="en_US">
              <template #icon>
                <icon-font type="icon-english" />
              </template>
              <span>English</span>
            </a-menu-item>
          </a-menu>
        </template>
        <a-avatar shape="square" size="small">
          <template #icon>
            <icon-font type="icon-language" />
          </template>
        </a-avatar>
      </a-dropdown>
      <a-dropdown>
        <template #overlay>
          <a-menu>
            <a-menu-item>
              <template #icon>
                <icon-font type="icon-settings" />
              </template>
              <span>个人设置</span>
            </a-menu-item>
            <a-menu-item>
              <template #icon>
                <icon-font type="icon-logout" />
              </template>
              <span>退出登录</span>
            </a-menu-item>
          </a-menu>
        </template>
        <a-avatar shape="square" size="small">
          <template #icon>
            <icon-font type="icon-default-user" />
          </template>
          {{ currentUser.nickname }}
        </a-avatar>
      </a-dropdown>
    </a-space>
  </div>
</template>

<script setup lang="ts">
import { useAppStore } from '@/store/app';
import { useI18n } from 'vue-i18n';
import { apply, randomTheme } from '../../hooks/useTheme';
import { IconFont } from '../IconFont/IconFont';
export type CurrentUser = {
  nickname: string;
  avatar?: string;
};
const t = useI18n();
defineProps<{
  currentUser: CurrentUser;
}>();

const currentLanguage = computed(() => {
  return [useAppStore().language];
});
const switchLanguage = ({ key }: { key: string }) => {
  t.locale.value = key;
  const html = document.querySelector('html');
  html && html.setAttribute('lang', key);
  useAppStore().changeLocale(key);
};
const handleClick = () => {
  apply(randomTheme());
};
</script>
