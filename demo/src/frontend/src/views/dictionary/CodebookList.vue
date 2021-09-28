<template>
  <a-card :title="`${(group && group.name) || ''} 字典数据`">
    <div v-if="group && group.key">
      <vxe-toolbar export custom>
        <template #buttons>
          <a-row :gutter="6" style="padding-right: 10px">
            <a-col :span="16">
              <a-button
                style="margin-right: 8px"
                @click="insertEvent"
                type="primary"
                size="small"
                icon="plus"
                >新增</a-button
              >
              <a-button
                type="primary"
                size="small"
                icon="save"
                @click="saveEvent"
                >保存</a-button
              >
              <a-popconfirm
                title="是否需要删除已经选中的字典项?"
                @confirm="removeEvent"
                ok-text="确定"
                cancel-text="取消"
                v-if="selected"
              >
                <a-button
                  type="danger"
                  size="small"
                  icon="delete"
                  style="margin: 0 8px"
                  >删除</a-button
                >
              </a-popconfirm>
            </a-col>
            <a-col :span="8">
              <a-input-search
                placeholder="请输入搜索关键词"
                v-model.trim="key"
                enter-button
                @search="handleSearch"
              />
            </a-col>
          </a-row>
        </template>
      </vxe-toolbar>
      <vxe-table
        :loading="loading"
        border
        :expand-config="{
          expandAll: true,
        }"
        highlight-current-row
        max-height="600"
        ref="xTree"
        keep-source
        :data="dictionaries"
        :export-config="{
          mode: 'all',
          isAllExpand: true,
          type: 'xlsx',
          filename: '数据字典导出',
          sheetName: '数据字典',
          original: true,
        }"
        :tree-config="{ children: 'children', expandAll: true }"
        :edit-rules="validRules"
        :edit-config="getEditConfig"
        @current-change="currentChangeEvent"
        @radio-change="radioChangeEvent"
        :radio-config="{ trigger: 'row', strict: false }"
      >
        <vxe-table-column width="40" type="radio"></vxe-table-column>
        <vxe-table-column
          width="160"
          field="key"
          title="编码"
          tree-node
          :edit-render="{
            name: 'AInput',
            props: { placeholder: '请填写字典项编码' },
          }"
        />
        <vxe-table-column
          field="index"
          title="序号"
          :edit-render="{
            name: 'AInputNumber',
            props: { placeholder: '请填写字典项序号', min: 0 },
          }"
        />
        <vxe-table-column
          field="value"
          show-overflow="tooltip"
          title="值"
          :edit-render="{
            name: 'AInput',
            props: { placeholder: '请填写字典项值' },
          }"
        />
        <vxe-table-column
          field="disabled"
          show-overflow="tooltip"
          title="状态"
          :edit-render="{
            name: 'ASwitch',
            type: 'visible',
          }"
        >
          <template #edit="{ row }">
            <a-switch v-model="row.disabled" />
          </template>
          <template #default="{ row }">
            <a-tag color="#f50" v-if="row.disabled"> 已禁用 </a-tag>
            <a-tag color="#87d068" v-else> 可用 </a-tag>
          </template>
        </vxe-table-column>
        <vxe-table-column
          field="description"
          show-overflow="tooltip"
          title="备注"
          :edit-render="{
            name: 'AInput',
            props: { type: 'textarea', placeholder: '请填写字典项备注信息' },
          }"
        />
      </vxe-table>
    </div>
    <a-alert v-else message="请在左侧选择字典分组" banner />
  </a-card>
</template>
<script lang="ts">
/* eslint-disable @typescript-eslint/no-explicit-any */

import { Vue, Component } from "vue-property-decorator";
import XEUtils from "xe-utils";
import { message } from "ant-design-vue";
import { RowInfo, Table } from "vxe-table";
@Component({
  components: {},
})
export default class CodebookList extends Vue {
  public group: dictionary.Group | null = null;
  public selected: dictionary.Codebook | null = null;
  public dictionaries: Array<dictionary.Codebook> = [];
  public cached: Array<dictionary.Codebook> = [];
  public key = "";
  public loading = false;
  public validRules = {
    code: [
      { required: true, message: "请填写字典项编码" },
      { min: 3, max: 50, message: "字典项编码长度在 3 到 50 个字符" },
    ],
    name: [
      { required: true, message: "请填写字典项名称" },
      { min: 2, max: 50, message: "字典项名称长度在 2 到 50 个字符" },
    ],
    description: [
      { min: 2, max: 255, message: "字典项描述长度在 2 到 255 个字符" },
    ],
  };

  get getEditConfig(): {
    trigger: string;
    mode: string;
    showStatus: boolean;
    autoClear: boolean;
  } {
    return {
      trigger: "click",
      mode: "row",
      showStatus: true,
      autoClear: false,
    };
  }

  public load(group?: dictionary.Group): void {
    this.group = group || null;
    if (!group || !group.id) {
      this.dictionaries = [];
      return;
    }
    this.loading = true;
    this.$api.dictionaryApi.codebook.byGroup(group.id, ({ data }) => {
      this.dictionaries = XEUtils.toArrayTree(data, {
        key: "key",
        parentKey: "parentKey",
        children: "children",
      });
      this.$nextTick(() => {
        (this.$refs.xTree as Table).setAllTreeExpand(true);
      });
      this.cached = this.dictionaries;
      this.selected = null;
      this.loading = false;
    });
  }

  async insertEvent(): Promise<void> {
    // 添加子子节点
    if (this.selected) {
      const row = await (this.$refs.xTree as Table).createRow({
        key: "",
        parentKey: this.selected ? this.selected.key : null,
        groupId: this.group?.id,
        index: 0,
      });
      if ((this.selected as RowInfo).children) {
        (this.selected as RowInfo).children.push(row);
      } else {
        (this.selected as RowInfo).children = [row];
      }
      (this.$refs.xTree as Table).setAllTreeExpand(true);
      (this.$refs.xTree as Table).setActiveRow(row);
    } else {
      // 插入平级节点
      (this.$refs.xTree as Table)
        .insertAt(
          {
            key: "",
            parentKey: null,
            groupId: this.group?.id,
            index: 0,
          },
          null
        )
        .then((newRow) => {
          (this.$refs.xTree as Table).setActiveRow(newRow.row);
        });
    }
  }
  handleSearch(): void {
    const key = XEUtils.toString(this.key).trim();
    if (key) {
      const options = { children: "children" };
      const searchProps = ["key", "value", "description"];
      this.dictionaries = XEUtils.searchTree(
        this.cached,
        (item) =>
          searchProps.some(
            (key1) => XEUtils.toString((item as any)[key1]).indexOf(key) > -1
          ),
        options
      );
      this.$nextTick(() => {
        (this.$refs.xTree as Table).setAllTreeExpand(true);
      });
    } else {
      this.load(this.group || undefined);
    }
  }
  currentChangeEvent({ row }: { row: dictionary.Codebook }): void {
    this.selected = row;
  }
  radioChangeEvent({ newValue }: { newValue: dictionary.Codebook }): void {
    this.selected = newValue;
  }
  removeEvent(): void {
    if (this.selected && this.selected.id) {
      this.$api.dictionaryApi.codebook.remove(this.selected.id, () => {
        this.messageAndReload("删除成功!");
      });
    } else {
      message.error("没有选择任何数据或选中数据还未保存!");
    }
  }
  messageAndReload(msg: string): void {
    this.$notification.success({
      message: "操作提示",
      description: msg,
      duration: 3,
      onClose: () => {
        this.load(this.group || undefined);
      },
    });
  }
  async saveEvent(): Promise<void> {
    const { insertRecords, removeRecords, updateRecords } = (
      this.$refs.xTree as Table
    ).getRecordset();
    if (insertRecords.length || removeRecords.length || updateRecords.length) {
      (this.$refs.xTree as Table).validate(true, (errMap: any) => {
        if (!errMap) {
          this.$api.dictionaryApi.codebook.vxeSave(
            {
              insertRecords: XEUtils.toTreeArray(
                insertRecords
              ) as Array<dictionary.Codebook>,
              updateRecords: XEUtils.toTreeArray(
                updateRecords
              ) as Array<dictionary.Codebook>,
              removeRecords: XEUtils.toTreeArray(
                removeRecords
              ) as Array<dictionary.Codebook>,
            },
            () => {
              this.messageAndReload("保存字典数据成功!");
            }
          );
        } else {
          console.log(errMap);
        }
      });
    } else {
      message.warning("数据未改动！");
    }
  }
}
</script>
<style lang="less" scoped></style>
