<template>
  <div>
    <a-row type="flex">
      <a-col :span="6">
        <a-input-search
          :placeholder="$t('page.role.search.placeholder')"
          v-model="key"
          @search="loadRoles(true)"
          enter-button
        />
      </a-col>
      <a-col :span="4" :offset="14">
        <a-button type="primary" icon="plus" @click="showRoleForm()">
          {{ $t("page.role.add") }}
        </a-button>
      </a-col>
    </a-row>
    <a-table
      :columns="columns"
      :data-source="pager.dataList"
      bordered
      :expanded-row-keys="expandedRowKeys"
      row-key="id"
      :loading="loading"
      @expand="expand"
      style="margin-top: 20px"
      @change="handleTableChange"
      :pagination="pagination"
      @expandedRowsChange="expandedRowsChange"
    >
      <template #action="role">
        <a-button-group size="small">
          <a-button type="primary" @click="showRoleForm(role)">
            <a-icon type="edit" />{{ $t("page.role.edit") }}
          </a-button>
          <a-popconfirm
            :title="$t('page.user.confirm.title')"
            :ok-text="$t('page.user.confirm.textYes')"
            :cancel-text="$t('page.user.confirm.textNo')"
            @confirm="deleteRole(role.id)"
          >
            <a-button type="danger">
              <a-icon type="delete" />{{ $t("page.role.delete") }}
            </a-button>
          </a-popconfirm>
        </a-button-group>
      </template>

      <template #expandedRowRender="role">
        <p>{{ role.description }}</p>
        <a-card size="small" :bordered="false" title="角色授权情况">
          <template #extra>
            <a-button
              size="small"
              type="primary"
              @click="showRolePermissionForm(role.id)"
            >
              <a-icon type="fire" />{{ $t("page.role.grant") }}
            </a-button>
          </template>
          <a-collapse
            v-if="role.permissionInfo && role.permissionInfo.length"
            expand-icon-position="right"
            :bordered="false"
            :active-key="allKeys(role.permissionInfo)"
          >
            <a-collapse-panel
              v-for="mod in role.permissionInfo"
              :key="mod.moduleKey"
              :header="mod.descr"
            >
              <a-tag color="cyan" v-for="(action, k) in mod.actions" :key="k">{{
                action.name
              }}</a-tag>
            </a-collapse-panel>
          </a-collapse>
          <a-empty description="角色没有任何权限" v-else />
        </a-card>
      </template>
    </a-table>
    <ModalForm
      :title="`${role && role.id ? '编辑' : '添加'}角色`"
      :rule="rule"
      @submited="saveOrUpdateRole"
      ref="roleForm"
    >
    </ModalForm>
    <RolePermissionForm
      ref="rolePermissionForm"
      @submited="queryPermissions"
    ></RolePermissionForm>
  </div>
</template>
<script lang="ts">
import { Component, Mixins } from "vue-property-decorator";
import { Mixin } from "@/utils/mixin";
import { AntPagination, Columns, RoleInfo } from "@/types";
import ModalForm from "@/components/ModalForm";
import roleForm from "./RoleFormDesign";
import RolePermissionForm from "./RolePermissionForm.vue";

@Component({
  components: { ModalForm, RolePermissionForm },
})
export default class RoleList extends Mixins(Mixin) {
  public key = "";
  public pager: acl.Pagination<acl.Role> = {
    pageNumber: 1,
    pageSize: 10,
    pageCount: 0,
    recordCount: 0,
    dataList: [],
  };
  public expandedRowKeys: Array<string> = [];

  public role: acl.Role | null = null;
  public loading = false;
  rule = roleForm;

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
  get columns(): Partial<Columns>[] {
    return [
      { title: this.$t("page.role.key"), dataIndex: "key", align: "center" },
      { title: this.$t("page.role.name"), dataIndex: "name", align: "center" },
      {
        title: this.$t("page.role.createdAt"),
        dataIndex: "createTime",
        align: "center",
      },
      {
        title: this.$t("page.role.action"),
        dataIndex: "",
        scopedSlots: { customRender: "action" },
        align: "center",
        width: "250px",
      },
    ];
  }

  created(): void {
    this.loadRoles();
  }

  allKeys(permissionInfo: Array<acl.ModuleInfo>): Array<string> {
    return permissionInfo.map((item) => item.moduleKey || "");
  }
  expand(
    expanded: boolean,
    role: { permissionInfo?: string[] } & acl.Role
  ): void {
    if (expanded && !role.permissionInfo) {
      this.$api.aclApi.role.permissions(role.id || 0, ({ data: info }) => {
        Object.assign(role, { permissionInfo: info.modules });
      });
    }
  }
  expandedRowsChange(expandedRows: Array<string>): void {
    this.expandedRowKeys = expandedRows;
  }
  handleTableChange(pagination: AntPagination): void {
    this.pager = {
      ...this.pager,
      pageNumber: pagination.current,
      pageSize: pagination.pageSize,
    };
    this.loadRoles();
  }
  deleteRole(id: number): void {
    this.$api.aclApi.role.remove(id, () => {
      this.messageAndReloadPage(
        this.$t("page.role.message.deleteSuccess") as string
      );
    });
  }
  showRoleForm(role?: acl.Role): void {
    this.role = role || null;
    (this.$refs.roleForm as ModalForm).show(role);
    (this.$refs.roleForm as ModalForm).disabled(!!role, ["key"]);
  }
  saveOrUpdateRole(data: { permissionInfo?: RoleInfo } & acl.Role): void {
    if (data.id) {
      this.$api.aclApi.role.edit(data, () => {
        this.messageAndReloadPage("更新角色成功");
      });
    } else {
      this.$api.aclApi.role.add(data, () => {
        this.messageAndReloadPage("创建角色成功");
      });
    }
  }
  messageAndReloadPage(msg: string): void {
    this.$notification.success({
      message: "操作提示",
      description: msg,
      duration: 3,
      onClose: () => {
        this.loadRoles();
      },
    });
  }

  showRolePermissionForm(id: number): void {
    (this.$refs.rolePermissionForm as RolePermissionForm).show(id);
  }
  queryPermissions(data: RoleInfo): void {
    if (data.id) {
      this.$api.aclApi.role.permissions(data.id || 0, ({ data: info }) => {
        this.pager.dataList.forEach((item) => {
          if (item.id === data.id) {
            (item as RoleInfo).permissionInfo = info.modules;
          }
        });
      });
    }
  }
  loadRoles(reset = false): void {
    this.loading = true;
    this.expandedRowKeys = [];
    this.$api.aclApi.role.search(
      {
        page: reset ? 1 : this.pager.pageNumber,
        size: this.pager.pageSize,
        key: this.key,
      },
      ({ data }) => {
        data.dataList.forEach((item) => {
          (item as RoleInfo).permissionInfo = undefined;
        });
        this.pager = data;
        this.loading = false;
      },
      () => {
        this.loading = false;
      }
    );
  }
}
</script>
<style lang="less" scoped></style>
