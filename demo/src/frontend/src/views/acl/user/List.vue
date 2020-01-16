<template>
  <div>
    <a-row type="flex">
      <a-col :span="6">
        <a-input-search
          :placeholder="$t('page.user.search.placeholder')"
          v-model="key"
          @search="loadUsers"
          enter-button
        />
      </a-col>
      <a-col :span="4" :offset="14">
        <a-button type="primary" icon="plus" @click="$refs.userForm.show()">{{
          $t('page.user.add')
        }}</a-button>
      </a-col>
    </a-row>
    <box :top="20"></box>
    <a-table
      :columns="columns"
      :data-source="pager.dataList"
      bordered
      row-key="id"
      @expand="expand"
    >
      <a-button-group slot="action" slot-scope="user" size="small">
        <a-button
          :title="$t('page.user.edit')"
          type="primary"
          icon="edit"
          @click="$refs.userForm.show(user)"
        />
        <a-button
          :title="$t('page.user.grant')"
          type="default"
          icon="fire"
          @click="$refs.userPermissionForm.show(user.id)"
        />
        <a-button
          :title="$t('page.user.role')"
          type="default"
          icon="flag"
          @click="$refs.userRoleForm.show(user.id)"
        />
        <a-popconfirm
          :title="$t('page.user.confirm.title')"
          @confirm="deleteUser(user.id)"
          :ok-text="$t('page.user.confirm.textYes')"
          :cancel-text="$t('page.user.confirm.textNo')"
        >
          <a-button
            :title="$t('page.user.delete')"
            type="danger"
            icon="delete"
          />
        </a-popconfirm>
      </a-button-group>

      <p slot="expandedRowRender" slot-scope="user" style="margin: 0">
        <a-row
          v-if="user.permissionInfo && user.permissionInfo.length"
          :gutter="24"
          :style="{marginBottom: '12px'}"
        >
          <a-col
            :span="12"
            v-for="(mod, index) in user.permissionInfo"
            :key="index"
            :style="{marginBottom: '12px'}"
          >
            <a-col :lg="4" :md="24">
              <span>{{ mod.descr }}：</span>
            </a-col>
            <a-col :lg="20" :md="24" v-if="mod.actions.length > 0">
              <a-tag color="cyan" v-for="(action, k) in mod.actions" :key="k">{{
                action.name
              }}</a-tag>
            </a-col>
            <a-col :span="20" v-else>-</a-col>
          </a-col>
        </a-row>
        <a-empty v-else />
      </p>
    </a-table>
    <UserForm ref="userForm" @submited="loadUsers"></UserForm>
    <UserPermissionForm
      ref="userPermissionForm"
      @submited="loadUsers"
    ></UserPermissionForm>
    <UserRoleForm ref="userRoleForm" @submited="loadUsers"></UserRoleForm>
    <div class="uuid_no_show">{{ AppModule.uuid }}</div>
  </div>
</template>
<script lang="ts">
import {Component, Prop, Vue, Mixins} from 'vue-property-decorator';
import Box from '@/components/Box';
import {Mixin} from '@/utils/mixin';
import UserForm from './UserForm.vue';
import UserPermissionForm from './UserPermissionForm.vue';
import UserRoleForm from './UserRoleForm.vue';
@Component({
  components: {Box, UserForm, UserPermissionForm, UserRoleForm},
})
export default class UserList extends Mixins(Mixin) {
  public key: string = '';
  public pager: defs.Pager<defs.User> = {
    pageNumber: 1,
    pageSize: 15,
    pageCount: 0,
    recordCount: 0,
    dataList: [],
  };

  get columns() {
    return [
      {title: this.$t('page.user.name'), dataIndex: 'userName'},
      {title: this.$t('page.user.mobile'), dataIndex: 'mobile'},
      {title: this.$t('page.user.createTime'), dataIndex: 'createTime'},
      {
        title: this.$t('page.user.action'),
        dataIndex: '',
        scopedSlots: {customRender: 'action'},
      },
    ];
  }

  created() {
    this.loadUsers();
  }
  expand(expanded: boolean, user: defs.User) {
    if (expanded && !(user as any).permissionInfo) {
      this.$api.user.permissions(user.id || 0, ({data: info}) => {
        //此处无法触发渲染更新
        Object.assign(user, {permissionInfo: info.modules});
        this.AppModule.refresh();
      });
    }
  }
  deleteUser(id: number) {
    this.$api.user.remove(id, () => {
      this.$message.success(
        this.$t('page.user.message.deleteSuccess'),
        2,
        () => {
          this.loadUsers();
        }
      );
    });
  }

  loadUsers() {
    this.$api.user.search(
      {page: this.pager.pageNumber, size: this.pager.pageSize, key: this.key},
      ({data}) => {
        this.pager = data;
      }
    );
  }
}
</script>
<style lang="less" scoped></style>
