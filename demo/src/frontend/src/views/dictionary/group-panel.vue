<template>
  <a-card :title="$t('page.dictionary.group.title')">
    <template #extra>
      <a-row :gutter="[5, 5]">
        <a-col :span="18">
          <a-input-search
            v-model:value="key"
            size="small"
            :placeholder="$t('page.dictionary.group.search.placeholder')"
            enter-button
            @search="loadGroups(1)"
          />
        </a-col>
        <a-col :span="6">
          <a-button type="primary" size="small" @click="addOrEditGroup()">
            <template #icon>
              <icon-font type="icon-add"></icon-font>
            </template>
            {{ $t('page.dictionary.group.add') }}
          </a-button>
        </a-col>
      </a-row>
    </template>
    <a-list item-layout="horizontal" :data-source="groupPage?.dataList">
      <template #renderItem="{ item }">
        <a-list-item>
          <a-list-item-meta :description="item.description">
            <template #title>
              <a-typography-text :delete="item.disabled">{{ item.name }}</a-typography-text>
            </template>
            <template #avatar>
              <radio-icon :selected="selectedKey === item.key" :value="item.key" @changed="checkChanged"></radio-icon>
            </template>
          </a-list-item-meta>
          <template #actions>
            <a-space>
              <a-button type="primary" size="small" @click="addOrEditGroup(item)">
                <template #icon>
                  <icon-font type="icon-edit"></icon-font>
                </template>
                {{ $t('page.dictionary.group.edit') }}
              </a-button>
              <a-popconfirm
                :title="$t('page.dictionary.group.confirm.delete.title', { name: item.name })"
                @confirm="deleteGroup(item.key)"
              >
                <a-button type="primary" danger size="small">
                  <template #icon>
                    <icon-font type="icon-delete"></icon-font>
                  </template>
                  {{ $t('page.dictionary.group.delete') }}
                </a-button>
              </a-popconfirm>
            </a-space>
          </template>
        </a-list-item>
      </template>
    </a-list>
    <modal-form
      ref="groupForm"
      v-model="group"
      :title="groupFormTitle"
      :rule="addOrEditGroupFromRule"
      @submit="doAOrEditGroup"
    ></modal-form>
  </a-card>
</template>
<script lang="ts" setup>
import { api } from '@/api';
import type { Pagination } from '@/api/api';
import { IconFont } from '@/components/IconFont/IconFont';
import ModalForm from '@/components/custom-form/modal-form.vue';
import RadioIcon from '@/components/radio-icon/radio-icon.vue';
import { groupFormRule } from './groupForm';
import { useI18n } from 'vue-i18n';
import { notification } from 'ant-design-vue';

const { t } = useI18n();
const key = ref('');

const groupPage = ref<Pagination<code.Group>>();

const loadGroups = (page = 1) => {
  api.codeApi.dictionary.groups(
    {
      page: page || groupPage.value?.pageNumber,
      size: groupPage.value?.pageSize,
      key: key.value,
    },
    data => {
      groupPage.value = data;
    },
  );
};

loadGroups();

const groupForm = ref<typeof ModalForm>();
const group = ref<Partial<code.Group>>({});
const groupFormTitle = computed(() => {
  return t(group.value.id ? 'page.dictionary.group.edit' : 'page.dictionary.group.add');
});
const addOrEditGroupFromRule = computed(() => {
  return groupFormRule();
});
const addOrEditGroup = (g?: code.Group) => {
  group.value = Object.assign({}, g);
  groupForm.value && groupForm.value.open();
};
const doAOrEditGroup = (g: code.Group) => {
  api.codeApi.dictionary.saveOrUpdateGroup(g, () => {
    groupForm.value && groupForm.value.close();
    notification.success({
      message: t('page.dictionary.group.notification.success'),
      description: t('page.dictionary.group.notification.addOrEdit.description'),
      onClose: () => {
        loadGroups(1);
      },
    });
  });
};

const deleteGroup = (key: string) => {
  api.codeApi.dictionary.deleteGroup(key, () => {
    notification.success({
      message: t('page.dictionary.group.notification.success'),
      description: t('page.dictionary.group.notification.delete.description'),
      onClose: () => {
        loadGroups(1);
      },
    });
  });
};

const emit = defineEmits<{
  (e: 'changed', group?: code.Group): void;
}>();
const selectedKey = ref('');
const checkChanged = (checked: boolean, value: string | number) => {
  selectedKey.value = checked ? String(value) : '';
  emit('changed', checked ? groupPage.value?.dataList.find(item => item.key === value) : undefined);
};
</script>
<style lang="less" scoped></style>
