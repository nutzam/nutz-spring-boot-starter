<template>
  <a-card :title="title">
    <template #extra>
      <a-button v-if="menuKey" size="small" type="primary" @click="addOrEditButton()">
        <template #icon>
          <icon-font type="icon-add" />
        </template>
        {{ $t('page.permission.button.add') }}
      </a-button>
    </template>
    <a-alert
      v-if="!menuKey"
      :message="$t('page.permission.button.alter.title')"
      :description="$t('page.permission.button.alter.description')"
      type="warning"
      show-icon
    />
    <a-table v-else :columns="columns" :data-source="buttons" :loading="loading">
      <template #bodyCell="{ column, record }">
        <template v-if="column.key === 'ops'">
          <a-button size="small" type="primary" @click="addOrEditButton(record)">
            <template #icon>
              <icon-font type="icon-edit" />
            </template>
            {{ $t('page.permission.button.edit') }}
          </a-button>
          <a-popconfirm
            :title="$t('page.permission.button.confirm.title', { name: record.name })"
            @confirm="deleteButton(record.key)"
          >
            <a-button size="small" type="primary" danger style="margin-left: 10px">
              <template #icon>
                <icon-font type="icon-delete" />
              </template>
              {{ $t('page.permission.button.delete') }}
            </a-button>
          </a-popconfirm>
        </template>
      </template>
    </a-table>
    <modal-form
      ref="buttonForm"
      v-model="button"
      :title="buttonFormTitle"
      :rule="addOrEditButtonFromRule"
      :disabled-fields="disabledFields"
      @submit="doAddOrEditButton"
    ></modal-form>
  </a-card>
</template>
<script lang="ts" setup>
import type { PropType } from 'vue';
import { useI18n } from 'vue-i18n';
import ModalForm from '@/components/custom-form/modal-form.vue';
import { notification } from 'ant-design-vue';
import { buttonFormRule } from './buttonForm';
import { api } from '@/api';
import { IconFont } from '@/components/IconFont/IconFont';
const { t } = useI18n();
const props = defineProps({
  menu: {
    type: Object as PropType<Partial<acl.Menu>>,
    required: true,
  },
});
const { menu } = toRefs(props);

const menuKey = computed(() => {
  return menu.value?.key || '';
});
const title = computed(() => {
  if (menuKey.value) {
    return t('page.permission.button.title', { name: menu.value.name });
  } else {
    return ``;
  }
});
const buttons = ref<Array<acl.Button>>([]);
const loading = ref(false);

const loadButtons = (menuKey: string) => {
  if (menuKey) {
    loading.value = true;
    api.aclApi.permission.buttons(menuKey, data => {
      loading.value = false;
      buttons.value = data;
    });
  } else {
    buttons.value = [];
  }
};

watch(
  menu,
  () => {
    loadButtons(menuKey.value);
  },
  { immediate: true, deep: true },
);
const columns = [
  {
    title: 'Key',
    dataIndex: 'key',
    key: 'key',
  },
  {
    title: '名称',
    dataIndex: 'name',
    key: 'name',
  },
  {
    title: '描述',
    dataIndex: 'description',
    key: 'description',
  },
  {
    title: '操作',
    dataIndex: 'ops',
    key: 'ops',
    width: 250,
  },
];

const button = ref<Partial<acl.Button>>({});
const buttonFormTitle = computed(() => {
  return t(button.value.id ? 'page.permission.button.edit' : 'page.permission.button.add');
});
const disabledFields = computed(() => {
  return button.value.id ? ['key'] : [];
});

const addOrEditButtonFromRule = computed(() => {
  return buttonFormRule();
});
const buttonForm = ref<typeof ModalForm>();
const addOrEditButton = (b?: acl.Button) => {
  button.value = Object.assign({ menuKey: menuKey.value }, b);
  buttonForm.value && buttonForm.value.open();
};
const doAddOrEditButton = (b: acl.Button) => {
  api.aclApi.permission.saveOrUpdateButton(b, () => {
    buttonForm.value && buttonForm.value.close();
    notification.success({
      message: t('page.permission.button.notification.success'),
      description: t('page.permission.button.notification.addOrEdit.description'),
      onClose: () => {
        loadButtons(menuKey.value);
      },
    });
  });
};

const deleteButton = (key: string) => {
  api.aclApi.permission.deleteButton(menuKey.value, key, () => {
    notification.success({
      message: t('page.permission.button.notification.success'),
      description: t('page.permission.button.notification.delete.description'),
      onClose: () => {
        loadButtons(menuKey.value);
      },
    });
  });
};
</script>
<style lang="less" scoped></style>
