<template>
  <page-container :title="$t(String($route.meta.title))" :sub-title="$t('page.role.subTitle')">
    <template #extra>
      <a-row>
        <a-col :span="16">
          <a-input-search
            v-model:value="key"
            :placeholder="$t('page.role.search.placeholder')"
            enter-button
            @search="loadRoles(1, 10, key)"
          />
        </a-col>
        <a-col :span="8">
          <a-button type="primary" style="margin-left: 10px" @click="addOrEditRole()">
            <template #icon>
              <icon-font type="icon-add" />
            </template>
            {{ $t('page.role.add') }}
          </a-button>
        </a-col>
      </a-row>
    </template>
    <div style="min-height: calc(100vh - 218px)">
      <a-table
        :columns="columns"
        :data-source="rolePage?.dataList"
        bordered
        :pagination="pagination"
        :loading="loading"
        row-key="key"
        @expand="tryLoadPermissions"
      >
        <template #expandedRowRender="{ record }">
          <permission-info-panel
            type="role"
            :ref-key="record.key"
            :name="record.name"
            :permissions="record.permissions"
          ></permission-info-panel>
        </template>
        <template #bodyCell="{ column, record }">
          <template v-if="column.dataIndex === 'operation'">
            <a-button size="small" type="primary" @click="addOrEditRole(record)">
              <template #icon>
                <icon-font type="icon-edit" />
              </template>
              {{ $t('page.role.edit') }}
            </a-button>
            <a-popconfirm
              :title="$t('page.role.confirm.delete.title', { name: record.name })"
              @confirm="deleteRole(record.key)"
            >
              <a-button size="small" type="primary" danger style="margin-left: 10px">
                <template #icon>
                  <icon-font type="icon-delete" />
                </template>
                {{ $t('page.role.delete') }}
              </a-button>
            </a-popconfirm>
          </template>
        </template>
      </a-table>
    </div>
    <modal-form
      ref="roleForm"
      v-model="role"
      :title="roleFormTitle"
      :rule="addOrEditRoleFromRule"
      :disabled-fields="disabledFields"
      @submit="doAOrEditRole"
    ></modal-form>
  </page-container>
</template>
<script lang="ts" setup>
import { api } from '@/api';
import type { Pagination } from '@/api/api';
import ModalForm from '@/components/custom-form/modal-form.vue';
import PermissionInfoPanel from '../components/permission-info-panel.vue';
import { IconFont } from '@/components/IconFont/IconFont';
import { notification } from 'ant-design-vue';
import { useI18n } from 'vue-i18n';
import { roleFormRule } from './roleForm';

const { t } = useI18n();
const key = ref('');
const loading = ref(false);
const rolePage = ref<Pagination<acl.Role>>();
const loadRoles = (page?: number, size?: number, searchKey?: string) => {
  loading.value = true;
  api.aclApi.role.roles(
    {
      page: page || rolePage.value?.pageNumber,
      size: size || rolePage.value?.pageSize,
      key: searchKey || key.value,
    },
    data => {
      loading.value = false;
      rolePage.value = data;
    },
  );
};
const columns = [
  {
    title: 'Key',
    dataIndex: 'key',
  },
  {
    title: '角色名',
    dataIndex: 'name',
  },
  {
    title: '角色描述',
    dataIndex: 'description',
  },
  {
    title: '操作',
    dataIndex: 'operation',
    width: 300,
  },
];
const pagination = computed(() => {
  return {
    current: rolePage.value?.pageNumber,
    pageSize: rolePage.value?.pageSize,
    total: rolePage.value?.recordCount,
    onChange: (page: number, pageSize: number) => {
      loadRoles(page, pageSize, key.value);
    },
  };
});

loadRoles();

const roleForm = ref<typeof ModalForm>();
const role = ref<Partial<acl.Role>>({});

const roleFormTitle = computed(() => {
  return t(role.value.id ? 'page.role.edit' : 'page.role.add');
});

const disabledFields = computed(() => {
  return role.value.id ? ['key'] : [];
});

const addOrEditRoleFromRule = computed(() => {
  return roleFormRule();
});

const addOrEditRole = (r?: acl.Role) => {
  role.value = Object.assign({}, r);
  roleForm.value && roleForm.value.open();
};

const doAOrEditRole = (role: acl.Role) => {
  api.aclApi.role.saveOrUpdateRole(role, () => {
    roleForm.value && roleForm.value.close();
    notification.success({
      message: t('page.role.notification.success'),
      description: t('page.role.notification.addOrEdit.description'),
      onClose: () => {
        loadRoles(1);
      },
    });
  });
};

const deleteRole = (key: string) => {
  api.aclApi.role.deleteRole(key, () => {
    notification.success({
      message: t('page.role.notification.success'),
      description: t('page.role.notification.delete.description'),
      onClose: () => {
        loadRoles(1);
      },
    });
  });
};

const tryLoadPermissions = (expanded: boolean, record: acl.Role & { permissions?: acl.Permission[] }) => {
  if (expanded && !record.permissions) {
    api.aclApi.role.permissions(record.key, data => {
      record.permissions = data;
    });
  }
};
</script>
<style lang="less" scoped></style>
