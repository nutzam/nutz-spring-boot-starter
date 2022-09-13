<template>
  <page-container :title="$t(String($route.meta.title))">
    <template #extra>
      <a-button key="3">
        <template #icon><icon-font type="icon-print" /></template>
        {{ $t('page.demos.print') }}
      </a-button>
      <a-button key="2">操作</a-button>
      <a-button key="1" type="primary">主操作</a-button>
    </template>
    <!-- 主内容区 -->
    <div style="min-height: calc(100vh - 218px)">
      <vxe-grid ref="xTable" :columns="columns" :data="departments"></vxe-grid>
    </div>
    <!--
    {{ $t('page.department.columns.id') }}
    {{ $t('page.department.columns.name') }}
    {{ $t('page.department.columns.members') }}
    {{ $t('page.department.columns.repositories') }}
    {{ $t('page.department.columns.description') }}
    -->
  </page-container>
</template>
<script lang="ts" setup>
const departments = ref([]);
const columns = [{}];
</script>
<style lang="less" scoped></style>
