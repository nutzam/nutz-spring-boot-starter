<template>
  <div>
    <a-row type="flex">
      <a-col :span="6">
        <a-input-search
          :placeholder="$t('page.role.search.placeholder')"
          v-model="key"
          @search="loadRoles"
          enter-button
        />
      </a-col>
      <a-col :span="4" :offset="14">
        <a-button type="primary" icon="plus" @click="$refs.roleForm.show()">{{
          $t('page.role.add')
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
      <a-button-group slot="action" slot-scope="role" size="small">
        <a-button
          :title="$t('page.role.edit')"
          type="primary"
          icon="edit"
          @click="$refs.roleForm.show(role)"
        />
        <a-button
          :title="$t('page.role.grant')"
          type="default"
          icon="fire"
          @click="$refs.rolePermissionForm.show(role.id)"
        />
        <a-popconfirm
          :title="$t('page.role.confirm.title')"
          @confirm="deleteRole(role.id)"
          :ok-text="$t('page.role.confirm.textYes')"
          :cancel-text="$t('page.role.confirm.textNo')"
        >
          <a-button
            :title="$t('page.role.delete')"
            type="danger"
            icon="delete"
          />
        </a-popconfirm>
      </a-button-group>

      <p slot="expandedRowRender" slot-scope="role" style="margin: 0;">
        <Box :top="20" :bottom="20">{{ role.description }}</Box>
        <a-row
          v-if="role.permissionInfo && role.permissionInfo.length"
          :gutter="24"
          :style="{marginBottom: '12px'}"
        >
          <a-col
            :span="12"
            v-for="(mod, index) in role.permissionInfo"
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
      </p>
    </a-table>
    <RoleForm ref="roleForm" @submited="loadRoles"></RoleForm>
    <RolePermissionForm
      ref="rolePermissionForm"
      @submited="loadRoles"
    ></RolePermissionForm>
    <div class="uuid_no_show">{{ AppModule.uuid }}</div>
  </div>
</template>
<script lang="ts">
import {Component, Prop, Vue, Mixins} from 'vue-property-decorator';
import Box from '@/components/Box/Box.vue';
import {Mixin} from '@/utils/mixin';
import {Pager, Role} from '@/types';
import RoleForm from './RoleForm.vue';
import RolePermissionForm from './RolePermissionForm.vue';
@Component({
  components: {Box, RoleForm, RolePermissionForm},
})
export default class RoleList extends Mixins(Mixin) {
  public key: string = '';
  public pager: defs.Pager<defs.Role> = {
    pageNumber: 1,
    pageSize: 15,
    pageCount: 0,
    recordCount: 0,
    dataList: [],
  };

  get columns() {
    return [
      {title: this.$t('page.role.key'), dataIndex: 'key'},
      {title: this.$t('page.role.name'), dataIndex: 'name'},
      {title: this.$t('page.role.createTime'), dataIndex: 'createTime'},
      {
        title: this.$t('page.role.action'),
        dataIndex: '',
        scopedSlots: {customRender: 'action'},
      },
    ];
  }

  created() {
    this.loadRoles();
  }
  expand(expanded: boolean, role: Role) {
    if (expanded && !(role as any).permissionInfo) {
      this.$api.role.permissions(role.id, ({data: info}) => {
        //此处无法触发渲染更新
        Object.assign(role, {permissionInfo: info.modules});
        this.AppModule.refresh();
      });
    }
  }
  deleteRole(id: number) {
    this.$api.role.remove(id, () => {
      this.$message.success(
        this.$t('page.role.message.deleteSuccess'),
        2,
        () => {
          this.loadRoles();
        }
      );
    });
  }

  loadRoles() {
    this.$api.role.search(
      {page: this.pager.pageNumber, size: this.pager.pageSize, key: this.key},
      ({data}) => {
        this.pager = data;
      }
    );
  }
}
</script>
<style lang="less" scoped></style>
