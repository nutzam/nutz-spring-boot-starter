<template>
  <div :key="value" :class="baseClassName">
    <a-tooltip v-for="item in options" :key="item.key" :title="item.title">
      <div :class="`${baseClassName}-item`" :style="item.disable ? disableStyle : {}" @click="handleChange(item)">
        <img :src="item.url" :alt="item.key" />
        <div :class="`${baseClassName}-selectIcon`" :style="{ display: value === item.key ? 'block' : 'none' }">
          <icon-font type="icon-checked" />
        </div>
      </div>
    </a-tooltip>
  </div>
</template>
<script lang="ts" setup>
import { IconFont } from '@/components/IconFont/IconFont';
const props = withDefaults(
  defineProps<{
    value: string;
    options: Array<{
      key: string;
      url: string;
      title: string;
      disable?: boolean;
    }>;
  }>(),
  { options: () => [] },
);
const { value, options } = toRefs(props);
const baseClassName = 'ant-pro-setting-drawer-block-checkbox';
const emit = defineEmits(['selectChange']);
const disableStyle = computed(() => {
  return { cursor: 'not-allowed' };
});
const handleChange = (item: { key: string; url: string; title: string; disable?: boolean }) => {
  !item.disable && emit('selectChange', item.key);
};
</script>
<style lang="less" scoped>
@ant-pro-setting-drawer: ~'@{ant-prefix}-pro-setting-drawer';
.@{ant-pro-setting-drawer} {
  &-block-checkbox {
    display: flex;

    &-item {
      position: relative;
      margin-right: 16px;
      border-radius: @border-radius-base;
      cursor: pointer;

      img {
        width: 48px;
      }
    }

    &-selectIcon {
      position: absolute;
      top: 0;
      right: 0;
      width: 100%;
      height: 100%;
      padding-top: 15px;
      padding-left: 24px;
      color: @primary-color;
      font-weight: bold;
      font-size: 14px;

      .action {
        color: @primary-color;
      }
    }
  }
}
</style>
