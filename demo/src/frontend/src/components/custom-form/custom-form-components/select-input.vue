<template>
  <a-input-group compact>
    <a-form-item-rest>
      <a-select :value="selectValue" style="width: 30%" :disabled="disabled" @update:value="updateSelectValue">
        <a-select-option v-for="(selectOption, index) in selectOptions" :key="index" :value="selectOption.value">
          {{ selectOption.name }}
        </a-select-option>
      </a-select>
    </a-form-item-rest>
    <a-input :value="inputValue" :disabled="disabled" style="width: 70%" @update:value="updateInputValue" />
  </a-input-group>
</template>
<script lang="ts" setup>
export type Option = {
  name: string;
  value: string;
};
const props = defineProps({
  modelValue: {
    type: Array<string>,
    default: ['', ''],
  },
  selectOptions: {
    type: Array<Option>,
    default: () => {
      return [];
    },
  },
  disabled: Boolean,
});
const { modelValue, selectOptions } = toRefs(props);
const emit = defineEmits(['update:modelValue']);
const selectValue = computed(() => {
  return modelValue.value[0];
});
const inputValue = computed(() => {
  return modelValue.value[1];
});
const updateSelectValue = (value: string) => {
  emit('update:modelValue', [value, inputValue.value]);
};
const updateInputValue = (value: string) => {
  emit('update:modelValue', [selectValue.value, value]);
};
</script>
