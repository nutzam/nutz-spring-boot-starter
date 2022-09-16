<template>
  <page-container :title="$t(String($route.meta.title))" :sub-title="$t('page.dictionary.subTitle')">
    <div style="min-height: calc(100vh - 218px)">
      <a-space direction="vertical" style="width: 100%">
        <a-alert
          :message="$t('page.dictionary.alter.title')"
          :description="$t('page.dictionary.alter.description')"
          type="warning"
          show-icon
          closable
        />
        <a-row :gutter="[12, 12]">
          <a-col :span="6">
            <group-panel @changed="groupChanged"></group-panel>
          </a-col>
          <a-col :span="18">
            <code-table :group="group"></code-table>
          </a-col>
        </a-row>
      </a-space>
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
