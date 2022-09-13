<template>
  <a-drawer v-model:visible="visible" :width="300" placement="right" :closable="false">
    <template #handle>
      <div class="ant-pro-setting-drawer-handle" @click="handleShowDrawer">
        <icon-font v-if="!visible" type="icon-cogs" />
        <icon-font v-else type="icon-close" />
      </div>
    </template>
    <div class="margin-bottom: 24px">
      <h3>{{ $t('layout.setting.navMode') }}</h3>
      <block-checkbox
        :value="String(modelValue.layout)"
        :options="layoutOptions"
        @select-change="layoutChange"
      ></block-checkbox>
      <a-divider />
      <h3>{{ $t('layout.setting.layout') }}</h3>
      <a-row style="margin-bottom: 12px">
        <a-col :span="12">{{ $t('layout.setting.fixedHeader') }}</a-col>
        <a-col :span="12" style="text-align: right">
          <a-switch
            :checked-children="$t('layout.setting.on')"
            :un-checked-children="$t('layout.setting.off')"
            :checked="modelValue.fixedHeader"
            @change="fixedHeaderChange"
          />
        </a-col>
      </a-row>
      <a-row style="margin-bottom: 12px">
        <a-col :span="12">{{ $t('layout.setting.fixSiderbar') }}</a-col>
        <a-col :span="12" style="text-align: right">
          <a-switch
            :checked-children="$t('layout.setting.on')"
            :un-checked-children="$t('layout.setting.off')"
            :checked="modelValue.fixSiderbar"
            @change="fixSiderbarChange"
          />
        </a-col>
      </a-row>
      <a-row style="margin-bottom: 12px">
        <a-col :span="12">{{ $t('layout.setting.splitMenus') }}</a-col>
        <a-col :span="12" style="text-align: right">
          <a-switch
            :checked-children="$t('layout.setting.on')"
            :un-checked-children="$t('layout.setting.off')"
            :checked="modelValue.splitMenus"
            @change="splitMenusChange"
          />
        </a-col>
      </a-row>

      <a-divider />
      <h3>{{ $t('layout.setting.content') }}</h3>
      <a-row style="margin-bottom: 12px">
        <a-col :span="12">{{ $t('layout.setting.showHeader') }}</a-col>
        <a-col :span="12" style="text-align: right">
          <a-switch
            :checked-children="$t('layout.setting.on')"
            :un-checked-children="$t('layout.setting.off')"
            :checked="modelValue.headerRender === undefined"
            @change="headerRenderChange"
          />
        </a-col>
      </a-row>
      <a-row style="margin-bottom: 12px">
        <a-col :span="12">{{ $t('layout.setting.showFooter') }}</a-col>
        <a-col :span="12" style="text-align: right">
          <a-switch
            :checked-children="$t('layout.setting.on')"
            :un-checked-children="$t('layout.setting.off')"
            :checked="modelValue.footerRender === undefined"
            @change="footerRenderChange"
          />
        </a-col>
      </a-row>
      <a-row style="margin-bottom: 12px">
        <a-col :span="12">{{ $t('layout.setting.showMenu') }}</a-col>
        <a-col :span="12" style="text-align: right">
          <a-switch
            disabled
            :checked-children="$t('layout.setting.on')"
            :un-checked-children="$t('layout.setting.off')"
            :checked="modelValue.menu === undefined"
            @change="menuChange"
          />
        </a-col>
      </a-row>
      <a-row style="margin-bottom: 12px">
        <a-col :span="12">{{ $t('layout.setting.showLogo') }}</a-col>
        <a-col :span="12" style="text-align: right">
          <a-switch
            :checked-children="$t('layout.setting.on')"
            :un-checked-children="$t('layout.setting.off')"
            :checked="modelValue.menuHeaderRender === undefined"
            @change="menuHeaderRenderChange"
          />
        </a-col>
      </a-row>
      <a-divider />
      <h3>{{ $t('layout.setting.themeColor') }}</h3>
      <theme-color-radio v-for="option in themeColors" :key="option.key" :option="option"></theme-color-radio>
    </div>
  </a-drawer>
</template>

<script setup lang="ts">
import { IconFont } from '@/components/IconFont/IconFont';
import dark from '@/assets/layout/dark.svg';
import top from '@/assets/layout/top.svg';
import mix from '@/assets/layout/mix.svg';
import { colorList } from '@/hooks/useTheme';
import BlockCheckbox from './block-checkbox.vue';
import ThemeColorRadio from './theme-color-radio.vue';
import { useAppStore } from '@/store/app';
import { useI18n } from 'vue-i18n';
type ConfType = 'layout' | 'fixedHeader' | 'fixSiderbar' | string;
const props = defineProps<{
  modelValue: Record<string, string | boolean | undefined>;
}>();

const themeColors = computed(() => {
  return colorList.map(item => {
    return Object.assign(item, { name: useI18n().t(`layout.setting.theme.${item.key}`) });
  });
});

const layoutOptions = [
  {
    key: 'side',
    title: '侧面导航',
    url: dark,
  },
  {
    key: 'top',
    title: '顶部导航',
    url: top,
  },
  {
    key: 'mix',
    title: '混合导航',
    url: mix,
  },
];
const emit = defineEmits(['update:modelValue']);

const visible = ref<boolean>(false);
const handleShowDrawer = () => {
  visible.value = !visible.value;
};

const layoutChange = (layout: 'side' | 'top' | 'mix') => {
  updateConf(layout, 'layout');
};

const fixedHeaderChange = (checked: boolean) => {
  updateConf(checked, 'fixedHeader');
};

const fixSiderbarChange = (checked: boolean) => {
  updateConf(checked, 'fixSiderbar');
};

const headerRenderChange = (checked: boolean) => {
  updateConf(checked && undefined, 'headerRender');
};
const splitMenusChange = (checked: boolean) => {
  updateConf(checked, 'splitMenus');
};
const footerRenderChange = (checked: boolean) => {
  updateConf(checked && undefined, 'footerRender');
};
const menuChange = (checked: boolean) => {
  updateConf(checked && undefined, 'menu');
};
const menuHeaderRenderChange = (checked: boolean) => {
  updateConf(checked && undefined, 'menuHeaderRender');
};

const updateConf = (val: string | boolean | undefined, type: ConfType) => {
  const newVal = {
    ...toRaw(props.modelValue),
    [`${type}`]: val,
  };
  useAppStore().updateConf(newVal);
  emit('update:modelValue', newVal);
};
</script>

<style lang="less">
.ant-pro-setting-drawer-handle {
  position: absolute;
  top: 240px;
  right: 300px;
  z-index: 0;
  display: flex;
  align-items: center;
  justify-content: center;
  width: 48px;
  height: 48px;
  font-size: 16px;
  text-align: center;
  background: var(--ant-primary-color);
  border-radius: 4px 0 0 4px;
  cursor: pointer;
  pointer-events: auto;

  > span {
    color: rgb(255, 255, 255);
    font-size: 20px;
  }
}
</style>
