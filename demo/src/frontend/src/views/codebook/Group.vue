<template>
  <div>
    <a-row :gutter="16">
      <a-col :span="16">
        <a-input
          :placeholder="$t('page.user.search.placeholder')"
          v-model="key"
        >
          <a-select
            slot="addonBefore"
            default-value="all"
            v-model="disabled"
            @change="loadGroups"
          >
            <a-select-option value="all">全部</a-select-option>
            <a-select-option value="true">禁用</a-select-option>
            <a-select-option value="false">可用</a-select-option>
          </a-select>
          <a-icon slot="addonAfter" type="search" @click="loadGroups" />
        </a-input>
      </a-col>
      <a-col :span="8">
        <a-button type="primary" icon="plus" @click="$refs.groupForm.show()">
          添加分组
        </a-button>
      </a-col>
    </a-row>
    <box :top="10"></box>
    <a-list
      :data-source="pager.dataList"
      size="small"
      item-layout="horizontal"
      :pagination="pagination"
    >
      <a-list-item slot="renderItem" slot-scope="group">
        <a-button
          slot="actions"
          type="primary"
          size="small"
          icon="edit"
          @click="$refs.groupForm.show(group)"
        ></a-button>
        <a-popconfirm
          title="确认删除这个分组?"
          @confirm="confirm(group.id)"
          slot="actions"
          ok-text="确认"
          cancel-text="取消"
        >
          <a-button type="danger" size="small" icon="delete"></a-button>
        </a-popconfirm>
        <a-list-item-meta :description="group.description">
          <a-avatar
            slot="avatar"
            icon="check"
            style="backgroundcolor: #87d068;"
            v-if="group.id === id"
          />
          <a-avatar slot="avatar" icon="close" v-else />
          <a
            slot="title"
            :title="group.key"
            @click="groupClicked(group.id)"
            v-if="group.disabled"
            style="text-decoration: line-through;"
          >
            {{ group.name }}
          </a>
          <a
            slot="title"
            :title="group.key"
            @click="groupClicked(group.id)"
            v-else
          >
            {{ group.name }}
          </a>
        </a-list-item-meta>
      </a-list-item>
      <div v-if="loading && !busy" class="demo-loading-container">
        <a-spin />
      </div>
    </a-list>
    <GroupForm @submited="loadGroups" ref="groupForm"></GroupForm>
  </div>
</template>
<script lang="ts">
import {Component, Prop, Vue, Emit} from 'vue-property-decorator';
import infiniteScroll from 'vue-infinite-scroll';

import Box from '@/components/Box';
import GroupForm from './GroupForm.vue';

@Component({
  components: {Box, GroupForm},
  directives: {infiniteScroll},
})
export default class GroupList extends Vue {
  loading: boolean = false;
  busy: boolean = false;
  disabled: boolean | string = 'all';
  id: number | null = null;
  public pager: defs.Pager<defs.Group> = {
    pageNumber: 1,
    pageSize: 10,
    pageCount: 0,
    recordCount: 0,
    dataList: [],
  };
  key: string = '';

  created() {
    this.loadGroups();
  }

  get pagination() {
    return {
      onChange: (page: number) => {
        this.pager.pageNumber = page;
        this.loadGroups();
      },
      pageSize: this.pager.pageSize,
      current: this.pager.pageNumber,
      total: this.pager.recordCount,
      size: 'small',
    };
  }
  groupClicked(id: number) {
    this.id = id;
    this.$emit('groupClicked', id);
  }
  confirm(id: number) {
    this.$api.group.remove(id, () => {
      this.$message.success('删除分组成功!', 2, () => {
        this.loadGroups();
      });
    });
  }

  loadGroups() {
    this.loading = true;
    this.$api.group.search(
      {
        key: this.key,
        page: this.pager.pageNumber,
        size: this.pager.pageSize,
        state:
          this.disabled === 'all'
            ? undefined
            : this.disabled === 'true'
            ? true
            : false,
      },
      ({data}) => {
        this.loading = false;
        this.pager = data;
        if (this.pager && this.pager.dataList.length) {
          this.id = this.pager.dataList[0].id || 0;
          this.$emit('groupClicked', this.id);
        }
      }
    );
  }
}
</script>
<style lang="less" scoped>
.demo-infinite-container {
  border: 1px solid #e8e8e8;
  border-radius: 4px;
  overflow: auto;
  padding: 8px 24px;
  max-height: 600px;
}
.demo-loading-container {
  position: absolute;
  bottom: 40px;
  width: 100%;
  text-align: center;
}
</style>
