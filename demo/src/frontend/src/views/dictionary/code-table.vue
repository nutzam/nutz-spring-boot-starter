<template>
  <a-card v-if="group" :title="$t('page.dictionary.code.title', { group: group?.name })">
    <template #extra>
      <a-button type="primary" size="small" @click="addOrEditCode()">
        <template #icon>
          <icon-font type="icon-add"></icon-font>
        </template>
        {{ $t('page.dictionary.code.add') }}
      </a-button>
    </template>
    <a-table :columns="columns" :data-source="treeData" :loading="loading">
      <template #bodyCell="{ column, record }">
        <template v-if="column.key === 'ops'">
          <a-space>
            <a-button size="small" type="primary" @click="addOrEditCode(record)">
              <template #icon>
                <icon-font type="icon-edit" />
              </template>
              {{ $t('page.dictionary.code.edit') }}
            </a-button>
            <a-button size="small" type="primary" @click="addOrEditCode({ parentKey: record.key })">
              <template #icon>
                <icon-font type="icon-add" />
              </template>
              {{ $t('page.dictionary.code.addChild') }}
            </a-button>
            <a-popconfirm
              :title="$t('page.dictionary.code.confirm.title', { name: record.key })"
              @confirm="deleteCode(record.key)"
            >
              <a-button size="small" type="primary" danger>
                <template #icon>
                  <icon-font type="icon-delete" />
                </template>
                {{ $t('page.dictionary.code.delete') }}
              </a-button>
            </a-popconfirm>
          </a-space>
        </template>
      </template>
    </a-table>
    <modal-form
      ref="codeForm"
      v-model="code"
      :title="codeFormTitle"
      :rule="addOrEditCodeFromRule"
      :disabled-fields="disabledFields"
      @submit="doAOrEditCode"
    ></modal-form>
  </a-card>
  <a-alert
    v-else
    :message="$t('page.dictionary.code.alter.title')"
    :description="$t('page.dictionary.code.alter.description')"
    type="info"
    show-icon
  />
</template>
<script lang="ts" setup>
import { api } from '@/api';
import { IconFont } from '@/components/IconFont/IconFont';
import ModalForm from '@/components/custom-form/modal-form.vue';

import type { PropType } from 'vue';
import { notification } from 'ant-design-vue';
import { codeFormRule } from './codeForm';
import { useI18n } from 'vue-i18n';
import XEUtils from 'xe-utils';
const { t } = useI18n();
const props = defineProps({
  group: {
    type: Object as PropType<code.Group>,
    required: false,
    default: () => undefined,
  },
});
const { group } = toRefs(props);
const groupKey = computed(() => {
  return group?.value?.key;
});
const codes = ref<code.Dictionary[]>([]);
const loading = ref(false);
const loadCodes = () => {
  loading.value = true;
  api.codeApi.dictionary.dictionaries(String(groupKey.value), data => {
    codes.value = data;
    loading.value = false;
  });
};

const treeData = computed(() => {
  return XEUtils.toArrayTree(codes.value, { parentKey: 'parentKey', key: 'key' });
});
watch(
  groupKey,
  newVal => {
    if (newVal) {
      loadCodes();
    }
  },
  { immediate: true },
);
const columns = [
  {
    title: '序号',
    dataIndex: 'index',
    key: 'index',
  },
  {
    title: 'Key',
    dataIndex: 'key',
    key: 'key',
  },
  {
    title: 'Value',
    dataIndex: 'value',
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
    width: 300,
  },
];

const codeForm = ref<typeof ModalForm>();
const code = ref<Partial<code.Dictionary>>({});
const codeFormTitle = computed(() => {
  return t(code.value.id ? 'page.dictionary.code.edit' : 'page.dictionary.code.add');
});
const disabledFields = computed(() => {
  return code.value.id ? ['key'] : [];
});
const addOrEditCodeFromRule = computed(() => {
  return codeFormRule();
});
const addOrEditCode = (c?: Partial<code.Dictionary>) => {
  code.value = Object.assign({}, c);
  codeForm.value && codeForm.value.open();
};
const doAOrEditCode = (c: code.Dictionary) => {
  api.codeApi.dictionary.saveOrUpdateDictionary(String(groupKey.value), c, () => {
    codeForm.value && codeForm.value.close();
    notification.success({
      message: t('page.dictionary.code.notification.success'),
      description: t('page.dictionary.code.notification.addOrEdit.description'),
      onClose: () => {
        loadCodes();
      },
    });
  });
};
const deleteCode = (key: string) => {
  api.codeApi.dictionary.deleteDictionary(String(groupKey.value), key, () => {
    notification.success({
      message: t('page.dictionary.code.notification.success'),
      description: t('page.dictionary.code.notification.delete.description'),
      onClose: () => {
        loadCodes();
      },
    });
  });
};
</script>
<style lang="less" scoped></style>
