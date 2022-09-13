<template>
  <form-create
    v-model:api="fApi"
    v-model="bindValue"
    :rule="bindRule"
    :option="bindOption"
    @change="onChange"
    @mounted="formDone"
  ></form-create>
</template>
<script lang="ts" setup>
import type { Api, FormRule, Options } from '@form-create/ant-design-vue';
import type { FormData } from '@form-create/core';
import type { PropType } from 'vue';
const props = defineProps({
  modelValue: {
    type: Object as PropType<FormData>,
    required: true,
  },
  option: {
    type: Object as PropType<Options>,
    required: false,
    default: () => {
      return {
        form: {
          hideRequiredMark: false,
          layout: 'horizontal',
          labelAlign: 'right',
          colon: true,
          labelCol: {
            span: 6,
          },
          wrapperCol: {
            span: 16,
            offset: 1,
          },
          validateOnRuleChange: true,
          //是否显示 label
          title: true,
        },
        submitBtn: false,
      };
    },
  },
  rule: {
    type: Array<FormRule>,
    required: true,
  },
  hiddenFields: {
    type: Array<string>,
    required: false,
    default: () => [],
  },
  disabledFields: {
    type: Array<string>,
    required: false,
    default: () => [],
  },
});
const { modelValue, option, rule, hiddenFields, disabledFields } = toRefs(props);

const emit = defineEmits<{
  (e: 'update:model-value', value: unknown): void;
  (e: 'changed', field: string, value: unknown): void;
}>();

// 表单数据的双向绑定
const bindValue = computed({
  get: function () {
    return props.modelValue;
  },
  set: function (value) {
    emit('update:model-value', value);
  },
});

const bindRule = computed(() => {
  if (!rule.value) return [];
  return rule.value.map(item => {
    if (item.inject !== false) {
      item.inject = true;
    }
    const props = item.props || {};
    if (props['multiple']) {
      props['mode'] = 'multiple';
    }
    if (props['filterable']) {
      props['show-search'] = true;
      props['filter-option'] = (input: string, option: { label: string }) => {
        const label = option.label;
        if (!label) {
          return false;
        }
        return !input || label.includes(input);
      };
    }
    return item;
  });
});

const bindOption = computed(() => {
  return Object.assign(
    {
      form: {
        hideRequiredMark: false,
        layout: 'horizontal',
        labelAlign: 'right',
        colon: true,
        labelCol: {
          span: 6,
        },
        wrapperCol: {
          span: 16,
          offset: 1,
        },
        validateOnRuleChange: true,
        //是否显示 label
        title: true,
      },
      submitBtn: false,
    },
    option.value,
  );
});

const fApi = ref<Api>();

watch(
  hiddenFields,
  (newVal, oldVal) => {
    fApi.value?.hidden(false, oldVal || []);
    fApi.value?.hidden(true, newVal);
  },
  { immediate: true, deep: true },
);
watch(
  disabledFields,
  (newVal, oldVal) => {
    fApi.value?.disabled(false, oldVal || []);
    fApi.value?.disabled(true, newVal);
  },
  { immediate: true, deep: true },
);

const validateForm = () => {
  return new Promise((resolve, reject) => {
    fApi.value?.submit(
      formData => {
        resolve(formData);
      },
      () => {
        reject();
      },
    );
  });
};

const reset = () => {
  fApi.value?.clearValidateState(fApi.value?.fields(), true);
  fApi.value?.resetFields();
  fApi.value?.reload(rule.value);
  bindValue.value = {};
  fApi.value?.coverValue({});
  fApi.value?.hidden(false, hiddenFields.value);
  fApi.value?.disabled(false, disabledFields.value);
};

const onChange = (field: string, value: unknown) => {
  emit('changed', field, value);
};

const formDone = (api: Api) => {
  nextTick(() => {
    api.coverValue(modelValue.value);
    api.reload(rule.value);
    api.hidden(true, hiddenFields.value);
    api.disabled(true, disabledFields.value);
  });
};

defineExpose({ validateForm, reset });
</script>
<style lang="less" scoped></style>
