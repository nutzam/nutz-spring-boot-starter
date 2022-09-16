<template>
  <div style="margin-right: 12px">
    <a-space>
      <a-avatar style="background-color: #f56a0000" @click="handleClick">
        <template #icon><icon-font type="icon-palette" style="font-size: 20px" /></template>
      </a-avatar>
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
        <a-avatar style="background-color: #f56a0000">
          <template #icon><icon-font type="icon-language" style="font-size: 20px" /></template>
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
            <a-menu-item @click="logout">
              <template #icon>
                <icon-font type="icon-logout" />
              </template>
              <span>退出登录</span>
            </a-menu-item>
          </a-menu>
        </template>
        <a-avatar :title="userName" style="background-color: #f56a00">{{ userName.substring(0, 1) }}</a-avatar>
      </a-dropdown>
    </a-space>
  </div>
</template>

<script setup lang="ts">
import { api } from '@/api';
import { useAppStore } from '@/store/app';
import { useUserStore } from '@/store/user';
import { useI18n } from 'vue-i18n';
import { apply, randomTheme } from '../../hooks/useTheme';
import { IconFont } from '../IconFont/IconFont';

const t = useI18n();

const router = useRouter();

const props = defineProps({
  userName: {
    type: String,
    required: false,
    default: () => '',
  },
});
const { userName } = toRefs(props);
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

const logout = () => {
  api.authApi.login.logout(() => {
    useUserStore().logout();
    router.push({ path: '/user' });
  });
};
</script>
<style lang="less" scoped>
.ant-avatar {
  cursor: pointer;
}
</style>
