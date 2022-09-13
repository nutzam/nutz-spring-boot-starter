<template>
  <div>
    <a-tooltip>
      <template #title>
        {{ option.name }}
      </template>
      <a-tag class="setting-drawer-theme-color-colorBlock" :color="option.color" @click="changeColor(option.color)">
        <template #icon>
          <icon-font v-if="checked" type="icon-checked" />
          <icon-font v-else type="icon-blank"></icon-font>
        </template>
      </a-tag>
    </a-tooltip>
  </div>
</template>
<script lang="ts" setup>
import { IconFont } from '@/components/IconFont/IconFont';
import { apply } from '@/hooks/useTheme';
import { useAppStore } from '@/store/app';
const props = withDefaults(
  defineProps<{
    option: {
      key: string;
      name: string;
      color: string;
      checked?: boolean;
    };
  }>(),
  {},
);
const { option } = toRefs(props);

const checked = computed(() => {
  return useAppStore().theme.primaryColor === option.value.color;
});

const changeColor = (color: string) => {
  apply(color);
  useAppStore().changePrimaryColor(color);
};
</script>
<style lang="less" scoped>
.setting-drawer-theme-color-colorBlock {
  width: 23px;
  height: 23px;
  border-radius: 2px;
  float: left;
  cursor: pointer;
  margin-right: 5px;
  padding-left: 0px;
  padding-right: 0px;
  text-align: center;
  color: #fff;
  font-weight: 700;
  .anticon {
    font-size: 16px;
    margin-left: -6px;
  }
}
</style>
