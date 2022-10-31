<template>
  <page-container :title="$t(String($route.meta.title))">
    <template #extra>
      <a-button type="primary" @click="syncAll">
        <template #icon>
          <icon-font type="icon-init"></icon-font>
        </template>
        同步全部
      </a-button>
      <a-button v-if="checkedKeys.length" type="primary" @click="syncSelected">
        <template #icon>
          <icon-font type="icon-sync"></icon-font>
        </template>
        同步选中
      </a-button>
    </template>
    <div style="min-height: calc(100vh - 218px)">
      <a-tree
        v-model:checkedKeys="checkedKeys"
        default-expand-all
        checkable
        :tree-data="menuTree"
        :field-names="{
          title: 'name',
        }"
      ></a-tree>
    </div>
  </page-container>
</template>
<script lang="ts">
export interface P {
  key: string;
  name: string;
  keyPath?: string;
  icon: string;
  parentKey?: string;
  children: P[];
}
</script>
<script lang="ts" setup>
import api from '@/api';
import { IconFont } from '@/components/IconFont/IconFont';
import { getMenuData, clearMenuItem } from '@ant-design-vue/pro-layout';
import { notification } from 'ant-design-vue';
import { computed } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRouter } from 'vue-router';
import XEUtils from 'xe-utils';
const router = useRouter();
const { t } = useI18n();
const { menuData } = getMenuData(clearMenuItem(router.getRoutes()));
const menuTree = computed(() => {
  return XEUtils.mapTree(menuData, item => {
    const title = String(item.meta?.title);
    if (item.children) {
      item.children.map(c => {
        return Object.assign(c, { parentKey: title.substring(6) });
      });
    }
    return {
      key: title.substring(6),
      name: t(title),
      icon: item.meta?.icon,
      parentKey: (item as unknown as { parentKey: string | undefined }).parentKey || '',
    };
  });
});
const permissionTree = computed(() => {
  return XEUtils.mapTree(menuTree.value, (item: P) => {
    const keyPath = item.keyPath || item.key;
    if (item.children) {
      item.children.map(c => {
        return Object.assign(c, { keyPath: c.keyPath ? item.keyPath : keyPath + '.' + c.key });
      });
    }
    return Object.assign(
      { id: item.key, name: item.name, icon: item.icon, parentId: item.parentKey },
      { keyPath: keyPath },
    );
  });
});
const checkedKeys = ref<string[]>([]);
const permissions = computed(() => {
  return XEUtils.toTreeArray(permissionTree.value, { clear: true }).map(item => {
    return Object.assign(item, { key: item.id, parentKey: item.parentId, id: undefined });
  });
});
const syncAll = () => {
  api.aclApi.permission.batchInitPermissions(permissions.value as unknown as acl.Permission[], () => {
    notification.success({
      message: '操作成功',
      description: '菜单初始化成功',
    });
  });
};
const syncSelected = () => {
  api.aclApi.permission.batchSyncPermissions(
    (permissions.value as unknown as acl.Permission[]).filter(
      item => checkedKeys.value.filter(key => key === item.key).length,
    ),
    () => {
      notification.success({
        message: '操作成功',
        description: '菜单同步成功',
      });
    },
  );
};
</script>
<style lang="less" scoped></style>
