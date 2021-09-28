<template>
  <div>
    <a-row type="flex">
      <a-col :span="6">
        <a-input-search
          :placeholder="$t('page.user.search.placeholder')"
          v-model="key"
          @search="loadUsers(true)"
          enter-button
        />
      </a-col>
      <a-col :span="4" :offset="14">
        <a-button type="primary" icon="plus" @click="showUserForm()">
          {{ $t("page.user.add") }}
        </a-button>
      </a-col>
    </a-row>
    <a-table
      bordered
      row-key="id"
      ref="table"
      style="margin-top: 20px"
      :columns="columns"
      :loading="loading"
      :data-source="pager.dataList"
      :expanded-row-keys="expandedRowKeys"
      :pagination="pagination"
      @expand="expand"
      @change="handleTableChange"
      @expandedRowsChange="expandedRowsChange"
    >
      <template #action="user">
        <a-button-group size="small">
          <a-button type="primary" @click="showUserForm(user)">
            <a-icon type="edit" />{{ $t("page.user.edit") }}
          </a-button>
          <a-popconfirm
            :title="$t('page.user.confirm.title')"
            :ok-text="$t('page.user.confirm.textYes')"
            :cancel-text="$t('page.user.confirm.textNo')"
            @confirm="deleteUser(user.id)"
          >
            <a-button type="danger">
              <a-icon type="delete" />{{ $t("page.user.delete") }}
            </a-button>
          </a-popconfirm>
        </a-button-group>
      </template>

      <!--展开信息 -->
      <template #expandedRowRender="user">
        <a-card size="small" :bordered="false" title="用户授权情况">
          <template #extra>
            <a-button-group size="small">
              <a-button type="primary" @click="showPermissionForm(user.id)">
                <a-icon type="fire" />{{ $t("page.user.grant") }}
              </a-button>
              <a-button type="primary" @click="showUserRoleForm(user.id)">
                <a-icon type="flag" /> {{ $t("page.user.role") }}
              </a-button>
            </a-button-group>
          </template>
          <a-collapse
            v-if="user.permissionInfo && user.permissionInfo.length"
            expand-icon-position="right"
            :bordered="false"
            :active-key="allKeys(user.permissionInfo)"
          >
            <a-collapse-panel
              v-for="mod in user.permissionInfo"
              :key="mod.moduleKey"
              :header="mod.descr"
            >
              <a-tag color="cyan" v-for="(action, k) in mod.actions" :key="k">{{
                action.name
              }}</a-tag>
            </a-collapse-panel>
          </a-collapse>
          <a-empty description="用户没有任何权限" v-else />
        </a-card>
      </template>
    </a-table>
    <!-- 新增和编辑弹窗 -->
    <ModalForm
      :title="`${user != undefined && user.id ? '编辑' : '添加'}用户`"
      :rule="rule"
      @submited="saveOrUpdateUser"
      ref="userForm"
    >
    </ModalForm>
    <UserPermissionForm
      ref="userPermissionForm"
      @submited="queryPermissions"
    ></UserPermissionForm>
    <UserRoleForm
      ref="userRoleForm"
      @submited="queryPermissions"
    ></UserRoleForm>
  </div>
</template>
<script lang="ts">
import ModalForm from "@/components/ModalForm";
import { AntPagination, Columns, UserInfo } from "@/types";
import { Mixin } from "@/utils/mixin";
import { Component, Mixins } from "vue-property-decorator";
import userForm from "./UserFormDesign";
import UserPermissionForm from "./UserPermissionForm.vue";
import UserRoleForm from "./UserRoleForm.vue";
@Component({
  components: { ModalForm, UserRoleForm, UserPermissionForm },
})
export default class UserList extends Mixins(Mixin) {
  public key = "";

  user: acl.User | null = null;

  public pager: acl.Pagination<acl.User> = {
    pageNumber: 1,
    pageSize: 10,
    pageCount: 0,
    recordCount: 0,
    dataList: [],
  };

  rule = userForm;

  public loading = false;
  public expandedRowKeys: Array<string> = [];

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
      { title: this.$t("page.user.name"), dataIndex: "name", align: "center" },
      {
        title: this.$t("page.user.mobile"),
        dataIndex: "mobile",
        align: "center",
      },
      {
        title: this.$t("page.user.createTime"),
        dataIndex: "createTime",
        align: "center",
      },
      {
        title: this.$t("page.user.action"),
        dataIndex: "",
        width: "300px",
        scopedSlots: { customRender: "action" },
        align: "center",
      },
    ];
  }

  created(): void {
    this.loadUsers();
  }
  allKeys(permissionInfo: Array<acl.ModuleInfo>): Array<string> {
    return permissionInfo.map((item) => item.moduleKey || "");
  }

  /**
   * 请求用户数据
   */
  loadUsers(reset = false): void {
    this.loading = true;
    this.expandedRowKeys = [];
    this.$api.aclApi.user.search(
      {
        page: reset ? 1 : this.pager.pageNumber,
        size: this.pager.pageSize,
        key: this.key,
      },
      ({ data }) => {
        data.dataList.forEach((item) => {
          (item as UserInfo).permissionInfo = undefined;
        });
        this.pager = data;
        this.loading = false;
      },
      () => {
        this.loading = false;
      }
    );
  }

  expandedRowsChange(expandedRows: Array<string>): void {
    this.expandedRowKeys = expandedRows;
  }

  /**
   * 展开权限信息
   */
  expand(expanded: boolean, user: UserInfo): void {
    if (expanded && !user.permissionInfo) {
      this.$api.aclApi.user.permissions(user.id || 0, ({ data: info }) => {
        Object.assign(user, { permissionInfo: info.modules });
      });
    }
  }
  /**
   * 表格变化加载用户数据,主要是分页变化
   */
  handleTableChange(pagination: AntPagination): void {
    this.pager = {
      ...this.pager,
      pageNumber: pagination.current,
      pageSize: pagination.pageSize,
    };
    this.loadUsers();
  }

  /**
   * 弹出新增和编辑框
   */
  showUserForm(user?: acl.User): void {
    this.user = user || null;
    (this.$refs.userForm as ModalForm).show(user);
    (this.$refs.userForm as ModalForm).hidden(!!user, ["password"]);
    (this.$refs.userForm as ModalForm).disabled(!!user, ["name"]);
  }

  /**
   * 新增和编辑框提交回调
   */
  saveOrUpdateUser(user: acl.User): void {
    if (user.id) {
      this.$api.aclApi.user.edit(user, () => {
        this.messageAndReloadPage("更新用户成功");
      });
    } else {
      this.$api.aclApi.user.add(user, () => {
        this.messageAndReloadPage("新增用户成功");
      });
    }
  }
  /**
   * 删除用户
   */
  deleteUser(id: number): void {
    this.$api.aclApi.user.remove(id, () => {
      this.messageAndReloadPage("删除用户成功");
    });
  }

  messageAndReloadPage(msg: string): void {
    this.$notification.success({
      message: "操作提示",
      description: msg,
      duration: 3,
      onClose: () => {
        this.loadUsers();
      },
    });
  }

  showPermissionForm(id: number): void {
    (this.$refs.userPermissionForm as UserPermissionForm).show(id);
  }

  showUserRoleForm(id: number): void {
    (this.$refs.userRoleForm as UserRoleForm).show(id);
  }

  queryPermissions(data: UserInfo): void {
    if (data.id) {
      this.$api.aclApi.user.permissions(data.id, ({ data: info }) => {
        this.pager.dataList.forEach((item) => {
          if (item.id === data.id) {
            Object.assign(item, { permissionInfo: info.modules });
          }
        });
      });
    }
  }
}
</script>
<style lang="less" scoped></style>
