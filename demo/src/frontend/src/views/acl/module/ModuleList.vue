<template>
  <div>
    <a-row type="flex">
      <a-col :span="6">
        <a-input-search
          :placeholder="$t('page.module.search.placeholder')"
          v-model="key"
          @search="loadModules(true)"
          enter-button
        />
      </a-col>
      <a-col :span="4" :offset="14">
        <a-button type="primary" icon="plus" @click="showModuleForm()">{{
          $t("page.module.add")
        }}</a-button>
      </a-col>
    </a-row>
    <a-table
      :columns="columns"
      :data-source="pager.dataList"
      bordered
      row-key="id"
      @expand="expand"
      :expanded-row-keys="expandedRowKeys"
      @expandedRowsChange="expandedRowsChange"
      style="margin-top: 20px"
      :pagination="pagination"
      :loading="loading"
      @change="handleTableChange"
    >
      <template #action="module">
        <a-button-group size="small">
          <a-button type="primary" icon="edit" @click="showModuleForm(module)">
            {{ $t("page.module.edit") }}
          </a-button>
          <a-popconfirm
            :title="$t('page.module.confirm.title')"
            @confirm="deleteModule(module.id)"
            :ok-text="$t('page.module.confirm.textYes')"
            :cancel-text="$t('page.module.confirm.textNo')"
          >
            <a-button type="danger" icon="delete">
              {{ $t("page.module.delete") }}
            </a-button>
          </a-popconfirm>
        </a-button-group>
      </template>

      <template #expandedRowRender="module">
        <a-descriptions title="" :column="1">
          <a-descriptions-item label="模块描述">
            {{ module.description }}
          </a-descriptions-item>
          <a-descriptions-item label="模块功能操作">
            <a-row :gutter="8">
              <a-col :span="24">
                <a-col :lg="24" :md="24">
                  <a-tag
                    color="cyan"
                    v-for="(action, k) in module.actions"
                    :key="k"
                    :closable="!action.installed"
                    @close.stop="deleteAction($event, action)"
                    @click="editAction(action)"
                    :visible="true"
                  >
                    {{ action.name }}
                  </a-tag>
                  <a-button
                    type="primary"
                    icon="plus"
                    size="small"
                    @click="addAction(module.id)"
                  ></a-button>
                </a-col>
              </a-col>
            </a-row>
          </a-descriptions-item>
        </a-descriptions>
      </template>
    </a-table>
    <ModalForm
      :title="`${!!module && module.id ? '编辑' : '添加'}模块`"
      :rule="moduleRule"
      @submited="saveOrUpdateModule"
      ref="moduleForm"
    >
    </ModalForm>
    <ModalForm
      :title="`${!!action && action.id ? '编辑' : '添加'}操作`"
      :rule="actionRule"
      @submited="saveOrUpdateAction"
      ref="actionForm"
    >
    </ModalForm>
  </div>
</template>
<script lang="ts">
import { Component, Mixins } from "vue-property-decorator";
import { Mixin } from "@/utils/mixin";
import { AntPagination, Columns } from "@/types";
import ModalForm from "@/components/ModalForm";
import moduleFrom from "./ModuleFormDesign";
import actionFrom from "./ActionFormDesign";

@Component({
  components: { ModalForm },
})
export default class ModuleList extends Mixins(Mixin) {
  public key = "";
  public loading = false;
  public pager: acl.Pagination<acl.Module> = {
    pageNumber: 1,
    pageSize: 15,
    pageCount: 0,
    recordCount: 0,
    dataList: [],
  };
  public expandedRowKeys: Array<string> = [];

  module: acl.Module | null = null;
  action: acl.Action | null = null;

  moduleRule = moduleFrom;
  actionRule = actionFrom;

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
      { title: this.$t("page.module.key"), dataIndex: "key", align: "center" },
      {
        title: this.$t("page.module.name"),
        dataIndex: "name",
        align: "center",
      },
      {
        title: this.$t("page.module.createTime"),
        dataIndex: "createTime",
        align: "center",
      },
      {
        title: this.$t("page.module.action"),
        dataIndex: "",
        scopedSlots: { customRender: "action" },
        align: "center",
        width: "200px",
      },
    ];
  }

  created(): void {
    this.loadModules();
  }

  deleteAction(e: Event, action: acl.Action): void {
    if (!action.installed && action.id) {
      this.$api.aclApi.action.remove(action.id, () => {
        this.messageAndReloadAction("删除操作成功", action.moduleId || 0);
      });
      return;
    }
    e.preventDefault();
    this.$message.warning("内置操作不能删除");
  }
  updataAction(id: number): void {
    if (!id) {
      return;
    }
    this.$api.aclApi.module.actions(id, ({ data }) => {
      Object.assign(this.pager, {
        dataList: this.pager.dataList.map((item) => {
          if (item.id === id) {
            Object.assign(item, { actions: data });
          }
          return item;
        }),
      });
    });
  }

  addAction(id: number): void {
    this.action = {
      moduleId: id,
      key: "",
      name: "",
      installed: false,
    };
    (this.$refs.actionForm as ModalForm).show(this.action);
    (this.$refs.actionForm as ModalForm).disabled(false, ["key"]);
  }
  editAction(action: acl.Action): void {
    if (!action.installed) {
      this.action = action;
      (this.$refs.actionForm as ModalForm).show(action);
      (this.$refs.actionForm as ModalForm).disabled(!!action.id, ["key"]);
      return;
    }
    this.$message.warning("内置操作不能编辑");
  }
  expand(expanded: boolean, module: { actions?: string[] } & acl.Module): void {
    if (expanded && !module.actions) {
      this.updataAction(module.id || 0);
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
    this.loadModules();
  }
  deleteModule(id: number): void {
    this.$api.aclApi.module.remove(id, () => {
      this.messageAndReloadPage(
        this.$t("page.module.message.deleteSuccess") as string
      );
    });
  }
  showModuleForm(module?: acl.Module): void {
    this.module = module || null;
    (this.$refs.moduleForm as ModalForm).show(module);
    (this.$refs.moduleForm as ModalForm).disabled(!!module, ["key"]);
  }

  saveOrUpdateAction(action: acl.Action): void {
    if (action.id) {
      this.$api.aclApi.action.edit(action, () => {
        this.messageAndReloadAction("更新操作成功", action.moduleId || 0);
      });
    } else {
      this.$api.aclApi.action.add(action, () => {
        this.messageAndReloadAction("创建操作成功", action.moduleId || 0);
      });
    }
  }

  messageAndReloadAction(msg: string, id: number): void {
    this.$notification.success({
      message: "操作提示",
      description: msg,
      duration: 3,
      onClose: () => {
        this.updataAction(id);
      },
    });
  }

  saveOrUpdateModule(data: { actions?: acl.ModuleInfo } & acl.Module): void {
    if (data.id) {
      this.$api.aclApi.module.edit(
        {
          id: data.id,
          name: data.name,
          key: data.key,
          description: data.description || "",
        },
        () => {
          this.messageAndReloadPage("更新模块成功");
        }
      );
    } else {
      this.$api.aclApi.module.add(data, () => {
        this.messageAndReloadPage("创建模块成功");
      });
    }
  }

  messageAndReloadPage(msg: string): void {
    this.$notification.success({
      message: "操作提示",
      description: msg,
      duration: 3,
      onClose: () => {
        this.loadModules();
      },
    });
  }
  loadModules(reset = false): void {
    this.loading = true;
    this.expandedRowKeys = [];
    this.$api.aclApi.module.search(
      {
        page: reset ? 1 : this.pager.pageNumber,
        size: this.pager.pageSize,
        key: this.key,
      },
      ({ data }) => {
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
