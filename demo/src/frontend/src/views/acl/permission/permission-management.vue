<template>
  <page-container :title="$t(String($route.meta.title))" :sub-title="$t('page.permission.subTitle')">
    <div style="min-height: calc(100vh - 218px)">
      <a-row :gutter="[32, 32]">
        <a-col :span="8">
          <menu-tree-panel @change="selectedChange"></menu-tree-panel>
        </a-col>
        <a-col :span="16">
          <button-table :menu="selectedMenu"></button-table>
        </a-col>
      </a-row>
    </div>
  </page-container>
</template>
<script lang="ts" setup>
import MenuTreePanel from './menu-tree-panel.vue';
import ButtonTable from './button-table.vue';
const selectedMenu = ref<Partial<acl.Menu>>({});
const selectedChange = (menu: acl.Menu, selected: boolean) => {
  if (selected) {
    selectedMenu.value = menu;
  } else {
    selectedMenu.value = {};
  }
};
</script>
<style lang="less" scoped></style>
