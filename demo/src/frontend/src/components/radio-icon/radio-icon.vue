<template>
  <div style="cursor: pointer" @click="onClick">
    <icon-font v-if="selected" type="icon-radio-checked" style="font-size: 36px"></icon-font>
    <icon-font v-else type="icon-radio" style="font-size: 36px"></icon-font>
  </div>
</template>
<script lang="ts" setup>
import { IconFont } from '../IconFont/IconFont';

const props = defineProps({
  selected: {
    type: Boolean,
    default: () => false,
  },
  value: {
    type: [String, Number],
    required: true,
  },
});
const { selected, value } = toRefs(props);

const emit = defineEmits<{
  (e: 'changed', checked: boolean, value: string | number): void;
}>();

const onClick = () => {
  emit('changed', !selected.value, value.value);
};
</script>
<style lang="less" scoped></style>
