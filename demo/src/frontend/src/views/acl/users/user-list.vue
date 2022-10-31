<template>
  <page-container :title="$t(String($route.meta.title))" :sub-title="$t('page.users.subTitle')">
    <template #extra>
      <a-row>
        <a-col :span="18">
          <a-input-search
            v-model:value="searchKey"
            :placeholder="$t('page.users.search.placeholder')"
            allow-clear
            enter-button
            @search="loadUsers()"
          >
            <template #addonBefore>
              <a-select
                v-model:value="selectedSex"
                allow-clear
                :placeholder="$t('page.users.sex.placeholder')"
                style="width: 80px"
              >
                <a-select-option v-for="sex in sexes" :key="sex.code" :value="sex.name">
                  {{ useAppStore().isCN ? sex.description : sex.code }}
                </a-select-option>
              </a-select>
            </template>
          </a-input-search>
        </a-col>
        <a-col :span="6">
          <a-button type="primary" style="margin-left: 10px" @click="addOrEditUser()">
            <template #icon>
              <icon-font type="icon-add" />
            </template>
            {{ $t('page.users.add') }}
          </a-button>
        </a-col>
      </a-row>
    </template>
    <div style="min-height: calc(100vh - 218px)">
      <a-table
        :columns="columns"
        :data-source="userPage?.dataList"
        bordered
        :pagination="pagination"
        :loading="loading"
        row-key="name"
        @expand="tryLoadPermissions"
      >
        <template #expandedRowRender="{ record }">
          <permission-info-panel
            type="user"
            :ref-key="record.name"
            :name="record.fullName"
            :permissions="record.permissions"
          ></permission-info-panel>
        </template>
        <template #bodyCell="{ column, record }">
          <template v-if="column.dataIndex === 'sex'">
            <icon-font v-if="record.sex === 'MALE'" type="icon-male" :title="record.sexInfo.description"></icon-font>
            <icon-font v-else type="icon-female" :title="record.sexInfo.description"></icon-font>
          </template>
          <template v-if="column.dataIndex === 'operation'">
            <a-button size="small" type="primary" @click="addOrEditUser(record)">
              <template #icon>
                <icon-font type="icon-edit" />
              </template>
              {{ $t('page.users.edit') }}
            </a-button>
            <a-popconfirm @confirm="resetPassword(record.name)">
              <template #title>
                <p>
                  {{ $t('page.users.confirm.reset.title', { name: record.name }) }}
                </p>
                <p>{{ $t('page.users.confirm.reset.description') }}</p>
              </template>
              <a-button size="small" type="primary" style="margin-left: 10px">
                <template #icon>
                  <icon-font type="icon-reset-password" />
                </template>
                {{ $t('page.users.reset') }}
              </a-button>
            </a-popconfirm>
            <a-popconfirm
              :title="$t('page.users.confirm.delete.title', { name: record.name })"
              @confirm="deleteUser(record.id)"
            >
              <a-button size="small" type="primary" danger style="margin-left: 10px">
                <template #icon>
                  <icon-font type="icon-delete" />
                </template>
                {{ $t('page.users.delete') }}
              </a-button>
            </a-popconfirm>
          </template>
        </template>
      </a-table>
    </div>
    <!-- 重置密码的回显框 -->
    <a-modal
      v-model:visible="passwordModalShow"
      :title="$t('page.users.modal.reset.title')"
      @ok="passwordModalShow = false"
    >
      <a-alert
        :message="$t('page.users.modal.reset.title')"
        :description="$t('page.users.modal.reset.description')"
        type="success"
        show-icon
      ></a-alert>
      <a-typography-paragraph copyable>{{ newPassword }}</a-typography-paragraph>
    </a-modal>
    <!-- 添加编辑用户弹窗 -->
    <modal-form
      ref="modalForm"
      v-model="user"
      :title="modalTitle"
      :rule="userFormRule"
      :loading="loading"
      :disabled-fields="disabledFields"
      :hidden-fields="hiddenFields"
      @submit="doAddUser"
    />
  </page-container>
</template>
<script lang="ts" setup>
import { api } from '@/api';
import type { Codebook, Pagination } from '@/api/api';
import { IconFont } from '@/components/IconFont/IconFont';
import ModalForm from '@/components/custom-form/modal-form.vue';
import PermissionInfoPanel from '../components/permission-info-panel.vue';
import { useAppStore } from '@/store/app';
import { notification } from 'ant-design-vue';
import { useI18n } from 'vue-i18n';
import { getUserFormRule } from './userForm';

const { t } = useI18n();
const sexes = ref<Codebook[]>([]);
api.aclApi.user.sexes(data => {
  sexes.value = data;
});

const searchKey = ref('');
const selectedSex = ref<'MALE' | 'FEMALE' | undefined>();
const userPage = ref<Pagination<acl.User>>();
const loading = ref(false);
const loadUsers = (sex: 'MALE' | 'FEMALE' | undefined = undefined, page = 1, size = 10) => {
  loading.value = true;
  api.aclApi.user.users(
    {
      page: page || userPage.value?.pageNumber,
      size: size || userPage.value?.pageSize,
      key: searchKey.value,
      sex: sex || selectedSex.value,
    },
    data => {
      loading.value = false;
      userPage.value = data;
    },
  );
};
loadUsers();
//表格列
const columns = [
  {
    title: '账号',
    dataIndex: 'name',
  },
  {
    title: '姓名',
    dataIndex: 'fullName',
  },
  {
    title: '电话',
    dataIndex: 'mobile',
  },
  {
    title: '邮箱',
    dataIndex: 'email',
  },
  {
    title: '性别',
    dataIndex: 'sex',
    width: 75,
  },
  {
    title: '操作',
    dataIndex: 'operation',
    width: 350,
  },
];

//分页信息
const pagination = computed(() => {
  return {
    current: userPage.value?.pageNumber,
    pageSize: userPage.value?.pageSize,
    total: userPage.value?.recordCount,
    onChange: (page: number, pageSize: number) => {
      console.log(page, pageSize);
      loadUsers(undefined, page, pageSize);
    },
  };
});

//待添加/编辑的用户
const user = ref<Partial<acl.User>>({});
//表单
const modalForm = ref<typeof ModalForm>();

//表单配置
const userFormRule = computed(() => {
  return getUserFormRule(sexes.value);
});

//禁用字段
const disabledFields = computed(() => {
  return user.value.id ? ['name'] : [];
});
const hiddenFields = computed(() => {
  return user.value.id ? ['password'] : [];
});

//弹窗标题
const modalTitle = computed(() => {
  return t(user.value.id ? 'page.users.edit' : 'page.users.add');
});

const addOrEditUser = (u?: acl.User) => {
  user.value = Object.assign({}, u || {});
  modalForm.value && modalForm.value.open();
};

//添加/编辑用户接口调用
const doAddUser = (user: acl.User) => {
  api.aclApi.user.saveOrUpdateUser(user, () => {
    modalForm.value && modalForm.value.close();
    notification.success({
      message: t('page.users.notification.success'),
      description: t('page.users.notification.addOrEdit.description'),
      onClose: () => {
        loadUsers(undefined, 1);
      },
    });
  });
};

const newPassword = ref('');
const passwordModalShow = ref(false);

//重置密码
const resetPassword = (name: string) => {
  api.aclApi.user.resetPassword(name, data => {
    newPassword.value = data;
    passwordModalShow.value = true;
  });
};

const deleteUser = (id: number) => {
  api.aclApi.user.deleteUser(id, () => {
    notification.success({
      message: t('page.users.notification.success'),
      description: t('page.users.notification.delete.description'),
      onClose: () => {
        loadUsers(undefined, 1);
      },
    });
  });
};
const tryLoadPermissions = (expanded: boolean, record: acl.User & { permissions?: acl.Permission[] }) => {
  if (expanded && !record.permissions) {
    api.aclApi.user.permissions(record.name, data => {
      record.permissions = data;
    });
  }
};
</script>
<style lang="less" scoped></style>
