<template>
  <a-card :title="$t('page.component.permission.info.panel.title', { name: name, type: typeName })">
    <a-collapse v-model:activeKey="activeKey">
      <a-collapse-panel v-for="(menu, index) in menus" :key="index" :header="menu.name" :show-arrow="false">
        <a-tag v-for="(button, i) in menu.buttons" :key="i" color="#2db7f5" :title="button.description">
          {{ button.name }}
        </a-tag>
      </a-collapse-panel>
    </a-collapse>
    <template #extra>
      <a-space>
        <a-button v-if="type === 'user'" type="primary" size="small" @click="showRoleForm">
          <template #icon>
            <icon-font type="icon-setting-role"></icon-font>
          </template>
          {{ $t('page.component.permission.info.panel.grantRole', { type: typeName }) }}
        </a-button>
        <a-button type="primary" size="small" @click="showPermissionForm">
          <template #icon>
            <icon-font type="icon-grant"></icon-font>
          </template>
          {{ $t('page.component.permission.info.panel.grant', { type: typeName }) }}
        </a-button>
      </a-space>
    </template>
    <permission-select-modal
      ref="permissionForm"
      :permissions="permissionInfos"
      @ok="doGrantPermission"
    ></permission-select-modal>
    <role-select-modal ref="roleForm" :roles="roleInfos" @ok="doGrantRole"></role-select-modal>
  </a-card>
</template>
<script lang="ts" setup>
import { IconFont } from '@/components/IconFont/IconFont';
import PermissionSelectModal from './permission-select-modal.vue';
import RoleSelectModal from './role-select-modal.vue';
import type { PropType } from 'vue';
import { api } from '@/api';
import { notification } from 'ant-design-vue';
import { useI18n } from 'vue-i18n';

const { t } = useI18n();
const props = defineProps({
  menus: {
    type: Array as PropType<Array<acl.MenuInfo>>,
    required: false,
    default: () => [],
  },
  type: {
    type: String as PropType<'role' | 'user'>,
    required: false,
    default: () => 'user',
  },
  name: {
    type: String,
    required: true,
  },
  refKey: {
    type: String,
    required: true,
  },
});
const { menus, name, type, refKey } = toRefs(props);

const activeKey = computed(() => {
  return new Array(menus.value?.length).fill(1).map((value, index) => {
    return `${index}`;
  });
});

const typeName = computed(() => {
  return type.value == 'user'
    ? t('page.component.permission.info.panel.user')
    : t('page.component.permission.info.panel.role');
});

const permissionForm = ref<typeof PermissionSelectModal>();
const permissionInfos = ref<acl.PermissionInfo[]>([]);
const showPermissionForm = () => {
  (type.value === 'user' ? api.aclApi.user : api.aclApi.role).permissionInfos(refKey.value, data => {
    permissionInfos.value = data;
    permissionForm.value && permissionForm.value.open();
  });
};

const doGrantPermission = (permissions: string[]) => {
  (type.value === 'user' ? api.aclApi.user : api.aclApi.role).grant(refKey.value, permissions, () => {
    permissionForm.value && permissionForm.value.close();
    notification.success({
      message: t('page.component.permission.info.panel.notification.success'),
      description: t('page.component.permission.info.panel.notification.grant.description', {
        name: name.value,
        type: typeName.value,
      }),
    });
  });
};
const roleForm = ref<typeof RoleSelectModal>();
const roleInfos = ref<acl.RoleInfo[]>([]);
const showRoleForm = () => {
  api.aclApi.user.roleInfos(refKey.value, data => {
    roleInfos.value = data;
    roleForm.value && roleForm.value.open();
  });
};

const doGrantRole = (permissions: string[]) => {
  api.aclApi.user.grantRole(refKey.value, permissions, () => {
    permissionForm.value && permissionForm.value.close();
    notification.success({
      message: t('page.component.permission.info.panel.notification.success'),
      description: t('page.component.permission.info.panel.notification.grantRole.description', {
        name: name.value,
      }),
    });
  });
};
</script>
<style lang="less" scoped></style>
