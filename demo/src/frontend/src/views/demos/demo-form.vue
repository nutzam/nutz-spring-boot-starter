<template>
  <page-container :title="$t(String($route.meta.title))">
    <div style="min-height: calc(100vh - 218px)">
      <create-form
        ref="demoForm"
        v-model="v"
        :rule="rule"
        :disabled-fields="disabledFields"
        :hidden-fields="hiddenFields"
      ></create-form>
      <a-button type="primary" @click="testValidateForm">test</a-button>
      <a-button type="primary" @click="disableField">禁用字段</a-button>
      <a-button type="primary" @click="hiddenField">隐藏字段</a-button>
      <a-button type="primary" @click="reset">重置表单</a-button>
      <a-button type="primary" @click="show">显示弹窗</a-button>
    </div>
    <modal-form
      ref="modalForm"
      v-model="v"
      :rule="rule"
      title="测试弹窗"
      :disabled-fields="disabledFields"
      :hidden-fields="hiddenFields"
      @submit="submitData"
    ></modal-form>
  </page-container>
</template>
<script lang="ts" setup>
import CreateForm from '@/components/custom-form/create-form.vue';
import ModalForm from '@/components/custom-form/modal-form.vue';
const v = ref({ qd05w0fghour: '123' });
const rule = ref([
  {
    type: 'input',
    field: 'qd05w0fghour',
    title: '输入框',
    info: '',
    _fc_drag_tag: 'input',
    hidden: false,
    display: true,
    $required: '',
  },
  {
    type: 'checkbox',
    field: 'hjs5w0fgi058',
    title: '多选框',
    info: '',
    effect: {
      fetch: '',
    },
    options: [
      {
        value: '1',
        label: '选项1',
      },
      {
        value: '2',
        label: '选项2',
      },
    ],
    _fc_drag_tag: 'checkbox',
    hidden: false,
    display: true,
  },
]);
const demoForm = ref<typeof CreateForm>();
const testValidateForm = () => {
  demoForm.value &&
    demoForm.value.validateForm().then((data: unknown) => {
      console.log(data);
    });
};

const disabledFields = ref<string[]>([]);
const disableField = () => {
  disabledFields.value = ['qd05w0fghour'];
};

const hiddenFields = ref<string[]>([]);
const hiddenField = () => {
  hiddenFields.value = ['qd05w0fghour'];
};

const reset = () => {
  demoForm.value && demoForm.value.reset();
  hiddenFields.value = [];
  v.value = { qd05w0fghour: '1234' };
};

const modalForm = ref<typeof ModalForm>();
const show = () => {
  disabledFields.value = ['qd05w0fghour'];
  v.value = { qd05w0fghour: '1234' };
  modalForm.value?.open();
};

const submitData = (data: unknown) => {
  console.log('submitData', data);
};
</script>
<style lang="less" scoped></style>
