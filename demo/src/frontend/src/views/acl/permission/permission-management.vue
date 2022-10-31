<template>
  <page-container :title="$t(String($route.meta.title))" :sub-title="$t('page.permission.subTitle')">
    <template #extra>
      <a-button type="primary" @click="addOrEditPermission">
        <template #icon>
          <icon-font type="icon-add" />
        </template>
        添加权限
      </a-button>
    </template>
    <div style="min-height: calc(100vh - 218px)">
      <vxe-table
        ref="xTree"
        border
        show-overflow
        size="medium"
        :loading="loading"
        :column-config="{ resizable: true }"
        :expand-config="{
          expandAll: true,
        }"
        :tree-config="{
          iconOpen: 'vxe-icon-square-minus',
          iconClose: 'vxe-icon-square-plus',
          transform: true,
          rowField: 'key',
          parentField: 'parentKey',
          expandAll: true,
          line: true,
        }"
        :data="permissions"
      >
        <vxe-column field="key" title="Key" tree-node></vxe-column>
        <vxe-column field="name" title="名称"></vxe-column>
        <vxe-column field="description" title="描述"></vxe-column>
        <vxe-column field="typeInfo.description" title="类型"></vxe-column>
        <vxe-column field="ops" title="操作" width="350">
          <template #default="{ row }">
            <a-button type="primary" size="small" @click="addOrEditPermission({ parentKey: row.key })">
              <template #icon>
                <icon-font type="icon-children" />
              </template>
              添加下级
            </a-button>
            <a-button type="primary" size="small" style="margin-left: 10px" @click="addOrEditPermission(row)">
              <template #icon>
                <icon-font type="icon-edit" />
              </template>
              编辑
            </a-button>
            <a-popconfirm :title="`确定要删除权限 ${row.name} ?`" @confirm="deletePermission(row.key)">
              <a-button size="small" type="primary" danger style="margin-left: 10px">
                <template #icon>
                  <icon-font type="icon-delete" />
                </template>
                删除
              </a-button>
            </a-popconfirm>
          </template>
        </vxe-column>
      </vxe-table>
    </div>
    <modal-form
      ref="permissionForm"
      v-model="permission"
      :title="permissionFormTitle"
      :rule="addOrEditPermissionFromRule"
      :disabled-fields="disabledFields"
      :hidden-fields="hiddenFields"
      @submit="doAOrEditPermission"
    ></modal-form>
  </page-container>
</template>
<script lang="ts" setup>
import type { VxeTableInstance } from 'vxe-table';
import { IconFont } from '@/components/IconFont/IconFont';
import api from '@/api';
import type { Codebook } from '@/api/api';
import { notification } from 'ant-design-vue';
import { permissionFormRule } from './permissionForm';
import ModalForm from '@/components/custom-form/modal-form.vue';

const loading = ref(true);
const permissions = ref<Array<acl.Permission>>([]);
const xTree = ref<VxeTableInstance>();
const loadPermissions = (force = false) => {
  loading.value = true;
  api.aclApi.permission.permissions({ force: force }, data => {
    loading.value = false;
    permissions.value = data;
    nextTick(() => {
      xTree.value && xTree.value.reloadData(data);
    });
  });
};
loadPermissions();

const types = ref<Array<Codebook>>([]);
api.aclApi.permission.types(data => {
  types.value = data;
});

const permissionForm = ref<typeof ModalForm>();
const permission = ref<Partial<acl.Permission>>({});
const permissionFormTitle = computed(() => {
  return permission.value && permission.value.id ? '编辑权限' : '添加权限';
});
const disabledFields = computed(() => {
  const key = permission.value && permission.value.id ? 'key' : '';
  const parentKey = permission.value && permission.value.parentKey ? 'parentKey' : '';
  return [key, parentKey];
});
const hiddenFields = computed(() => {
  return permission.value && permission.value.parentKey ? [] : ['parentKey'];
});
const addOrEditPermissionFromRule = computed(() => {
  return permissionFormRule(types.value);
});
const addOrEditPermission = (p?: Partial<acl.Permission>) => {
  permission.value = Object.assign({ type: 'MENU' }, p);
  permissionForm.value && permissionForm.value.open();
};
const doAOrEditPermission = (permission: acl.Permission) => {
  const f = permission.id ? api.aclApi.permission.updatePermission : api.aclApi.permission.savePermission;
  delete permission.typeInfo;
  console.log(permission);
  f(permission, () => {
    permissionForm.value && permissionForm.value.close();
    notification.success({
      message: '操作成功',
      description: '权限数据操作成功,页面将自动刷新!',
      onClose: () => {
        loadPermissions(true);
      },
    });
  });
};

const deletePermission = (key: string) => {
  api.aclApi.permission.deletePermission(key, () => {
    notification.success({
      message: '操作成功',
      description: '删除权限成功,页面将自动刷新!',
      onClose: () => {
        loadPermissions(true);
      },
    });
  });
};
</script>
<style lang="less" scoped></style>
