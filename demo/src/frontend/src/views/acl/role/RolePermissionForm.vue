<template>
  <a-modal
    title="角色授权"
    :width="750"
    :visible="visible"
    @ok="handleSubmit"
    :confirm-loading="confirmLoading"
    centered
    @cancel="handleCancel"
  >
    <a-form :form="form" @submit="handleSubmit">
      <a-form-item
        label="功能操作"
        :label-col="labelCol"
        :wrapper-col="wrapperCol"
      >
        <a-transfer
          :data-source="allActions"
          :titles="['待选权限', '已选权限']"
          :filter-option="filterOption"
          show-search
          :target-keys="targetKeys"
          :selected-keys="selectedKeys"
          :render="transferRender"
          @selectChange="handleSelectChange"
          @change="handleActionChange"
          :list-style="{
            width: '225px',
            height: '300px',
          }"
          v-decorator="['actionIds', {}]"
        />
      </a-form-item>
    </a-form>
  </a-modal>
</template>
<script lang="ts">
import { FormCol } from "@/types";
import { WrappedFormUtils } from "ant-design-vue/types/form/form";
import { Component, Vue, Watch } from "vue-property-decorator";
@Component({
  components: {},
})
export default class RolePermissionForm extends Vue {
  visible = false;
  confirmLoading = false;
  public roleId = 0;
  public form!: WrappedFormUtils;
  selectedKeys: Array<string> = [];
  targetKeys: Array<string> = [];
  modules: Array<acl.ModuleInfo> = [];
  labelCol: FormCol = {
    xs: { span: 24 },
    sm: { span: 5 },
  };
  wrapperCol: FormCol = {
    xs: { span: 24 },
    sm: { span: 17 },
  };

  @Watch("allActions")
  watchAllActions(): void {
    this.targetKeys =
      this.allActions.length === 0
        ? []
        : this.allActions
            .filter((item) => item.selected)
            .map((item) => item.key || "");
  }

  transferRender(item: { title: string }): string {
    return item.title;
  }

  get allActions(): Partial<acl.ActionInfo>[] {
    if (!this.modules || !this.modules.length) return [];
    return this.modules
      .map((item) =>
        (item.actions || []).map((action: acl.ActionInfo) =>
          Object.assign(action, {
            mod: {
              key: item.moduleKey,
              name: item.moduleName,
              id: item.moduleId,
            },
          })
        )
      )
      .reduce((x, y) => {
        if (x == null) return y;
        return x.concat(y || []);
      })
      .map((item) => ({
        key: `${item.mod.key}.${item.key}`,
        title: `${item.mod.name} - ${item.name}`,
        description: `${item.mod.name} - ${item.name}`,
        selected: item.selected,
      }));
  }

  created(): void {
    this.form = this.$form.createForm(this);
  }

  filterOption(inputValue: string, option: { title: string }): boolean {
    return option.title.indexOf(inputValue) > -1;
  }

  handleSelectChange(
    sourceSelectedKeys: Array<string>,
    targetSelectedKeys: Array<string>
  ): void {
    this.selectedKeys = [...sourceSelectedKeys, ...targetSelectedKeys];
  }

  handleActionChange(nextTargetKeys: Array<string>): void {
    this.targetKeys = nextTargetKeys;
  }

  public show(id: number): void {
    this.form.resetFields();
    this.visible = true;
    this.roleId = id;
    this.loadModuleInfos(id);
  }

  public loadModuleInfos(roleId: number): void {
    this.$api.aclApi.role.grantInfo(roleId, {}, ({ data: modules }) => {
      this.modules = modules;
    });
  }

  handleSubmit(): void {
    this.confirmLoading = true;
    this.form.validateFields(
      (errors: Array<Error>, values: { actionIds: Array<string> }) => {
        if (!errors) {
          this.confirmLoading = true;
          this.$api.aclApi.role.grant(
            this.roleId,
            values.actionIds,
            () => {
              this.handleCancel();
              this.$notification.success({
                message: "操作提示",
                description: "角色授权成功",
                duration: 3,
                onClose: () => {
                  this.$emit("submited", { id: this.roleId });
                },
              });
            },
            (error) => {
              this.confirmLoading = false;
              this.$notification.error({
                message: "Error",
                description: error,
              });
            }
          );
        }
      }
    );
  }
  handleCancel(): void {
    this.confirmLoading = false;
    this.visible = false;
    this.selectedKeys = [];
  }
}
</script>
<style lang="less" scoped></style>
