<template>
  <a-card title="字典分组">
    <a-row :gutter="4">
      <a-col :span="19" style="padding-right: 4px">
        <a-input placeholder="请输入分组名称/描述" v-model="key">
          <a-select
            slot="addonBefore"
            default-value="all"
            v-model="disabled"
            @change="loadGroups(true)"
          >
            <a-select-option value="all">全部</a-select-option>
            <a-select-option value="true">禁用</a-select-option>
            <a-select-option value="false">可用</a-select-option>
          </a-select>
          <a-icon slot="addonAfter" type="search" @click="loadGroups(true)" />
        </a-input>
      </a-col>
      <a-col :span="5">
        <a-button type="primary" @click="showGroupForm()"> 添加 </a-button>
      </a-col>
    </a-row>
    <a-space direction="vertical" size="small"><span></span></a-space>
    <a-list
      :data-source="pager.dataList"
      size="small"
      item-layout="horizontal"
      :pagination="pagination"
    >
      <template #renderItem="group">
        <a-list-item>
          <a-button
            slot="actions"
            type="primary"
            size="small"
            icon="edit"
            @click="showGroupForm(group)"
          ></a-button>
          <!-- <a-popconfirm
            title="确认删除这个分组?"
            @confirm="deleteGroup(group.id)"
            slot="actions"
            ok-text="确认"
            cancel-text="取消"
          >
            <a-button type="danger" size="small" icon="delete"></a-button>
          </a-popconfirm> -->
          <a-list-item-meta :description="group.description">
            <Iconfont
              slot="avatar"
              :type="code === group.key ? 'ats-xuanzhong' : 'ats-daixuan'"
              @click="groupClicked(group)"
            ></Iconfont>
            <a slot="title" :title="group.key" @click="groupClicked(group)">
              {{ group.name }}
            </a>
          </a-list-item-meta>
        </a-list-item>
      </template>
      <div v-if="loading && !busy" class="demo-loading-container">
        <a-spin />
      </div>
    </a-list>
    <ModalForm
      ref="groupForm"
      :title="`${!!group && group.id ? '编辑' : '添加'}分组`"
      :rule="rule"
      @submited="saveOrUpdateGroup"
    ></ModalForm>
  </a-card>
</template>
<script lang="ts">
import { Vue, Component } from "vue-property-decorator";
import { AntPagination } from "@/types";
import ModalForm from "@/components/ModalForm";
import groupForm from "./GroupFormDesign";
@Component({
  components: { ModalForm },
})
export default class GroupList extends Vue {
  public key = "";
  disabled: boolean | string = "all";
  public loading = false;
  public busy = false;
  public code = "";
  public pager: dictionary.Pagination<dictionary.Group> = {
    pageNumber: 1,
    pageSize: 10,
    pageCount: 0,
    recordCount: 0,
    dataList: [],
  };

  public group: dictionary.Group | null = null;

  rule = groupForm;

  get pagination(): AntPagination {
    return {
      current: this.pager.pageNumber,
      pageSize: this.pager.pageSize,
      total: this.pager.recordCount,
      showSizeChanger: true,
      showQuickJumper: true,
      hideOnSinglePage: true,
      size: "small",
    };
  }

  created(): void {
    this.loadGroups();
  }

  groupClicked(group: dictionary.Group): void {
    this.code = group.key || "";
    this.$emit("groupClicked", group);
  }

  loadGroups(reset = false): void {
    this.loading = true;
    this.code = "";
    this.$api.dictionaryApi.group.search(
      {
        page: reset ? 1 : this.pager.pageNumber,
        size: this.pager.pageSize,
        key: this.key,
        state: this.disabled == "all" ? undefined : this.disabled == "true",
      },
      ({ data: pager }) => {
        this.pager = pager;
        this.loading = false;
        this.$emit("groupClicked", {});
      }
    );
  }

  showGroupForm(group?: dictionary.Group): void {
    this.group = group || null;
    (this.$refs.groupForm as ModalForm).show(group);
    (this.$refs.groupForm as ModalForm).disabled(!!group, ["key"]);
  }
  saveOrUpdateGroup(group: dictionary.Group): void {
    if (group.id) {
      this.$api.dictionaryApi.group.edit(group, () => {
        this.messageAndReload("编辑分组成功");
      });
    } else {
      this.$api.dictionaryApi.group.add(group, () => {
        this.messageAndReload("创建分组成功");
      });
    }
  }
  deleteGroup(id: number): void {
    this.$api.dictionaryApi.group.remove(id, () => {
      this.messageAndReload("禁用分组成功");
    });
  }
  messageAndReload(msg: string): void {
    this.$notification.success({
      message: "操作提示",
      description: msg,
      duration: 3,
      onClose: () => {
        this.loadGroups(true);
      },
    });
  }
}
</script>
<style lang="less" scoped></style>
