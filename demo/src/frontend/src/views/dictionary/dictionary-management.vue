<template>
  <page-container :title="$t(String($route.meta.title))" :sub-title="$t('page.dictionary.subTitle')">
    <div style="min-height: calc(100vh - 218px)">
      <a-row :gutter="[32, 32]">
        <a-col :span="8">
          <group-panel @changed="groupChanged"></group-panel>
        </a-col>
        <a-col :span="16">
          <code-table :group="group"></code-table>
        </a-col>
      </a-row>
    </div>
  </page-container>
</template>
<script lang="ts" setup>
import GroupPanel from './group-panel.vue';
import CodeTable from './code-table.vue';

const group = ref<code.Group>();
const groupChanged = (g?: code.Group) => {
  group.value = g;
};
</script>
<style lang="less" scoped></style>
