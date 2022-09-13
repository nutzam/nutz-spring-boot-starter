<template>
  <page-container :title="$t(String($route.meta.title))">
    <template #extra>
      <a-button key="3" @click="printTable">
        <template #icon><icon-font type="icon-print" /></template>
        {{ $t('page.demos.print') }}
      </a-button>
      <a-button key="2">操作</a-button>
      <a-button key="1" type="primary">主操作</a-button>
    </template>
    <!-- 主内容区 -->
    <div style="min-height: calc(100vh - 218px)">
      <vxe-grid ref="xTable" :columns="columns" :data="teams"></vxe-grid>
    </div>
    <!--
    {{ $t('page.demos.columns.id') }}
    {{ $t('page.demos.columns.name') }}
    {{ $t('page.demos.columns.members') }}
    {{ $t('page.demos.columns.repositories') }}
    {{ $t('page.demos.columns.description') }}
    -->
  </page-container>
</template>
<script lang="ts" setup>
import { IconFont } from '@/components/IconFont/IconFont';
import { http } from '@/plugins/axios';
import dayjs from 'dayjs';
import type { VxeGridInstance, VxeGridPropTypes } from 'vxe-table/types/grid';
import type { VxeTableDefines } from 'vxe-table/types/table';
const teams = ref([]);
const columns: VxeGridPropTypes.Columns = [
  {
    title: 'page.demos.columns.id',
    field: 'id',
  },
  {
    title: 'page.demos.columns.name',
    field: 'name',
  },
  {
    title: 'page.demos.columns.members',
    field: 'members',
  },
  {
    title: 'page.demos.columns.repositories',
    field: 'repositories',
  },
  {
    title: 'page.demos.columns.description',
    field: 'description',
    showOverflow: 'tooltip',
  },
];

const printColumns = computed(() => {
  return columns.slice(0, columns.length - 1) as VxeTableDefines.ColumnInfo[];
});

const xTable = ref<VxeGridInstance>();

const printTable = () => {
  const printStyle = `
            .title {
              text-align: center;
            }
            .my-list-row {
              display: inline-block;
              width: 100%;
            }
            .my-list-col {
              float: left;
              width: 33.33%;
              height: 28px;
              line-height: 28px;
            }
            .my-top,
            .my-bottom {
              font-size: 12px;
            }
            .my-top {
              margin-bottom: 5px;
            }
            .my-bottom {
              margin-top: 30px;
              text-align: right;
            }
            `;

  // 打印顶部内容模板
  const topHtml = `
            <h1 class="title">团队情况</h1>
            <div class="my-top">
              <div class="my-list-row">
                <div class="my-list-col">部门名称：研发部</div>
                <div class="my-list-col">打印序号：X2665847132654</div>
                <div class="my-list-col">打印日期：${dayjs().format('YYYY-MM-DD HH:mm:ss')}</div>
              </div>
              <div class="my-list-row">
                <div class="my-list-col">打印人：黎曼云</div>
                <div class="my-list-col">公司地址：银河系地球村</div>
                <div class="my-list-col">联系电话：188 **** 8888</div>
              </div>
            </div>
            `;

  // 打印底部内容模板
  const bottomHtml = `
            <div class="my-bottom">
              <div class="my-list-row">
                <div class="my-list-col"></div>
                <div class="my-list-col">创建人：黎曼云</div>
                <div class="my-list-col">创建日期：${dayjs().format('YYYY-MM-DD HH:mm:ss')}</div>
              </div>
            </div>
            `;
  xTable.value &&
    xTable.value.print({
      sheetName: '打印团队情况',
      style: printStyle,
      columns: printColumns.value,
      beforePrintMethod: ({ content }: { content: string }) => {
        // 拦截打印之前，返回自定义的 html 内容
        return topHtml + content + bottomHtml;
      },
    });
};
http.get('/project/teams').then(data => {
  teams.value = data.data;
});
</script>
<style lang="less" scoped></style>
