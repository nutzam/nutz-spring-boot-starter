<template>
  <a-card :title="$t('page.permission.menu.title')">
    <template #extra>
      <a-row>
        <a-col :span="18">
          <a-input-search
            v-model:value="key"
            size="small"
            :placeholder="$t('page.permission.menu.search.placeholder')"
            enter-button
            @search="loadMenus(key)"
          />
        </a-col>
        <a-col :span="6">
          <a-button size="small" type="primary" @click="addOrEditMenu()">
            <template #icon>
              <icon-font type="icon-add" />
            </template>
            {{ $t('page.permission.menu.add') }}
          </a-button>
        </a-col>
      </a-row>
    </template>
    <a-tree
      :tree-data="treeData"
      :show-line="{ showLeafIcon: false }"
      :loading="loading"
      auto-expand-parent
      block-node
      @select="selectMenu"
    >
      <template #title="record">
        <a-dropdown :trigger="['contextmenu']">
          <a-tooltip>
            <template #title>{{ record.description }}</template>
            {{ record.name }}
          </a-tooltip>
          <template #overlay>
            <a-menu>
              <a-menu-item key="edit" @click="addOrEditMenu(record)">
                <template #icon>
                  <icon-font type="icon-edit" />
                </template>
                {{ $t('page.permission.menu.edit') }}
              </a-menu-item>
              <a-menu-item key="add" @click="addOrEditMenu({ parentKey: record.key })">
                <template #icon>
                  <icon-font type="icon-add" />
                </template>
                {{ $t('page.permission.menu.addChild') }}
              </a-menu-item>
              <a-popconfirm :title="`确定要删除菜单 ${record.name} ?`" @confirm="deleteMenu(record.key)">
                <a-menu-item key="delete">
                  <template #icon>
                    <icon-font type="icon-delete" />
                  </template>
                  {{ $t('page.permission.menu.delete') }}
                </a-menu-item>
              </a-popconfirm>
            </a-menu>
          </template>
        </a-dropdown>
      </template>
    </a-tree>
    <modal-form
      ref="menuForm"
      v-model="menu"
      :title="menuFormTitle"
      :rule="addOrEditMenuFromRule"
      :disabled-fields="menuFormDisabledFields"
      @submit="doAddOrEditMenu"
    ></modal-form>
  </a-card>
</template>
<script lang="ts" setup>
import { api } from '@/api';
import XEUtils from 'xe-utils';
import ModalForm from '@/components/custom-form/modal-form.vue';
import { notification } from 'ant-design-vue/es';
import { menuFormRule } from './menuForm';
import { IconFont } from '@/components/IconFont/IconFont';
import { useI18n } from 'vue-i18n';
const { t } = useI18n();

const key = ref('');
const menus = ref<(acl.Menu & { buttons?: acl.Button[] })[]>();
const loading = ref(false);
const loadMenus = (searchKey?: string) => {
  loading.value = true;
  api.aclApi.permission.menus({ key: searchKey || key.value }, data => {
    loading.value = false;
    menus.value = data;
  });
};
loadMenus();

const treeData = computed(() => {
  return XEUtils.toArrayTree(menus.value, { parentKey: 'parentKey', key: 'key' });
});

const menu = ref<Partial<acl.Menu>>({});
const addOrEditMenuFromRule = computed(() => {
  return menuFormRule();
});
const menuForm = ref<typeof ModalForm>();
const menuFormTitle = computed(() => {
  return t(menu.value.id ? 'page.permission.menu.edit' : 'page.permission.menu.add');
});
const menuFormDisabledFields = computed(() => {
  return menu.value.id ? ['key'] : [];
});
const addOrEditMenu = (m?: Partial<acl.Menu>) => {
  menu.value = Object.assign({}, m || {});
  menuForm.value && menuForm.value.open();
};
const doAddOrEditMenu = (menu: acl.Menu) => {
  api.aclApi.permission.saveOrUpdateMenu(menu, () => {
    menuForm.value && menuForm.value.close();
    notification.success({
      message: t('page.permission.menu.notification.success'),
      description: t('page.permission.menu.notification.addOrEdit.description'),
      onClose: () => {
        loadMenus();
      },
    });
  });
};

const deleteMenu = (key: string) => {
  api.aclApi.permission.deleteMenu(key, () => {
    notification.success({
      message: t('page.permission.menu.notification.success'),
      description: t('page.permission.menu.notification.delete.description'),
      onClose: () => {
        loadMenus();
      },
    });
  });
};

const emit = defineEmits(['change']);

const selectMenu = (
  selectedKeys: string[],
  { selected, selectedNodes }: { selected: boolean; selectedNodes: Array<acl.Menu> },
) => {
  emit('change', selectedNodes[0], selected);
};
</script>
<style lang="less" scoped></style>
