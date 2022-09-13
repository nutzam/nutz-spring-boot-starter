<template>
  <a-modal
    v-model:visible="visible"
    :mask-closable="false"
    :title="title"
    :width="width"
    :confirm-loading="confirmLoading"
    :ok-text="$t('component.modalForm.ok')"
    :cancel-text="$t('component.modalForm.cancel')"
    @ok="handleOk"
    @cancel="reset"
  >
    <create-form
      v-if="visible"
      ref="createForm"
      v-model="bindValue"
      :option="option"
      :rule="rule"
      :hidden-fields="hiddenFields"
      :disabled-fields="disabledFields"
      v-bind="$attrs"
      @changed="dataChanged"
    />
  </a-modal>
</template>
<script lang="ts" setup>
import CreateForm from '@/components/custom-form/create-form.vue';
import type { FormRule, Options } from '@form-create/ant-design-vue';
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
  title: {
    type: String,
    required: false,
    default: () => '',
  },
  width: {
    type: [String, Number],
    required: false,
    default: () => 550,
  },
});
const { title, width, option, rule, hiddenFields, disabledFields } = toRefs(props);
const emit = defineEmits<{
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  (e: 'submit', value: any): void;
  (e: 'changed', field: string, value: unknown): void;
  (e: 'update:model-value', value: unknown): void;
}>();
//双向绑定
const bindValue = computed({
  get: function () {
    return props.modelValue;
  },
  set: function (value) {
    emit('update:model-value', value);
  },
});

const visible = ref(false);

const confirmLoading = ref(false);
const createForm = ref<typeof CreateForm>();

const handleOk = () => {
  confirmLoading.value = true;
  createForm.value
    ?.validateForm()
    .then((value: FormData) => {
      emit('submit', value);
    })
    .catch(() => {
      confirmLoading.value = false;
    });
};

const reset = () => {
  createForm.value?.reset();
  confirmLoading.value = false;
};

const dataChanged = (field: string, value: unknown) => {
  emit('changed', field, value);
};
const open = () => {
  visible.value = true;
  confirmLoading.value = false;
};

const close = () => {
  reset();
  visible.value = false;
};

defineExpose({ open, close });
</script>
<style lang="less" scoped></style>
