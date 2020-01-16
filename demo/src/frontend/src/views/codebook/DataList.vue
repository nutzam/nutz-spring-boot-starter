<template>
  <div>
    <vxe-toolbar :data="codebooks" export setting>
      <template v-slot:buttons>
        <vxe-button @click="insertEvent">新增</vxe-button>
        <vxe-button @click="removeEvent">删除</vxe-button>
        <vxe-button @click="saveEvent">保存</vxe-button>
      </template>
    </vxe-toolbar>
    <vxe-table
      border
      highlight-current-row
      ref="xTree"
      :data="codebooks"
      :tree-config="{children: 'children', expandAll: true}"
      :edit-rules="validRules"
      :edit-config="{trigger: 'click', mode: 'row', showStatus: true}"
      @current-change="currentChangeEvent"
      :radio-config="{trigger: 'row'}"
    >
      <vxe-table-column width="40" type="radio"></vxe-table-column>
      <vxe-table-column
        type="index"
        width="160"
        title="序号"
        tree-node
      ></vxe-table-column>
      <vxe-table-column title="基本信息">
        <vxe-table-column
          field="key"
          title="Key"
          :edit-render="{name: 'AInput', props: {placeholder: '请填写码本Key'}}"
        ></vxe-table-column>
        <vxe-table-column
          field="value"
          title="Value"
          :edit-render="{
            name: 'AInput',
            props: {placeholder: '请填写码本Value'},
          }"
        ></vxe-table-column>
      </vxe-table-column>
      <vxe-table-column
        :filters="[
          {label: '可用', value: false},
          {label: '不可用', value: true},
        ]"
        field="disabled"
        title="状态"
        :edit-render="{name: 'ASwitch'}"
      >
        <template v-slot="{row}">
          <a-tag v-if="row.disabled" color="#f50">已禁用</a-tag>
          <a-tag v-else color="#87d068">可用</a-tag>
        </template>
      </vxe-table-column>
      <vxe-table-column
        field="index"
        title="排序"
        :edit-render="{name: 'AInputNumber'}"
      ></vxe-table-column>
      <vxe-table-column
        field="description"
        title="描述"
        :edit-render="{
          name: 'AInput',
          props: {type: 'textarea', placeholder: '请填写码本描述信息'},
        }"
        show-overflow
      ></vxe-table-column>
    </vxe-table>
  </div>
</template>
<script lang="ts">
import {Component, Prop, Vue} from 'vue-property-decorator';
import XEUtils from 'xe-utils';
@Component({
  components: {},
})
export default class DataList extends Vue {
  public groupId: number = 0;
  public selected: defs.CodeBook | null = null;
  public codebooks: Array<defs.CodeBook> = [];
  public validRules: any = {
    key: [
      {required: true, message: '请填写码本Key'},
      {min: 3, max: 50, message: '码本Key长度在 3 到 50 个字符'},
    ],
    value: [
      {required: true, message: '请填写码本Value'},
      {min: 2, max: 50, message: '码本Value长度在 2 到 50 个字符'},
    ],
    description: [
      {min: 2, max: 255, message: '码本描述长度在 2 到 255 个字符'},
    ],
  };
  public load(id: number) {
    this.groupId = id;
    this.loadCodeBooks();
  }

  loadCodeBooks() {
    this.$api.codeBook.byGroup(this.groupId, ({data: codeBooks}) => {
      this.codebooks = XEUtils.toArrayTree(codeBooks, {
        key: 'id',
        parentKey: 'parentId',
        children: 'children',
      });
    });
  }
  insertEvent() {
    if (this.selected && !this.selected.id) {
      this.$message.error('选中行数据没有保存,不能添加下级节点!');
      return;
    }
    (this.$refs.xTree as any)
      .createRow({
        groupId: this.groupId,
        parentId: this.selected ? this.selected.id : 0,
        index: -1,
        key: '',
      })
      .then((newRow: defs.CodeBook) => {
        if (this.selected) {
          if ((this.selected as any).children) {
            (this.selected as any).children.push(newRow);
          } else {
            (this.selected as any).children = [newRow];
          }
        } else {
          this.codebooks.unshift(newRow);
        }
        (this.$refs.xTree as any)
          .refreshData()
          .then(() => (this.$refs.xTree as any).setActiveRow(newRow));
        (this.$refs.xTree as any).setAllTreeExpansion(true);
      });
  }
  currentChangeEvent({row}: {row: defs.CodeBook}) {
    this.selected = row;
  }
  removeEvent() {
    if (this.selected && this.selected.id) {
      this.$api.codeBook.remove(this.selected.id, result => {
        this.$message.error('删除成功!', 2, () => {
          this.loadCodeBooks();
        });
      });
    } else {
      this.$message.error('没有选择任何数据或选中数据还未保存!');
    }
  }
  saveEvent() {
    let {insertRecords, removeRecords, updateRecords} = (this.$refs
      .xTree as any).getRecordset();
    if (insertRecords.length || removeRecords.length || updateRecords.length) {
      (this.$refs.xTree as any).fullValidate((valid: boolean, errMap: any) => {
        if (valid) {
          this.$api.codeBook.vxeSave(
            {insertRecords, updateRecords, removeRecords},
            () => {
              this.$message.success('保存成功!', 2, () => {
                this.loadCodeBooks();
              });
            }
          );
        } else {
          console.log(errMap);
        }
      });
    } else {
      this.$message.warning('数据未改动！');
    }
  }
  getRemoveEvent() {}
  getUpdateEvent() {}
}
</script>
<style lang="less" scoped></style>
