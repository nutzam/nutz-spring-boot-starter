<template>
  <a-modal
    title="用户角色设置"
    :width="750"
    :visible="visible"
    @ok="handleSubmit"
    :confirm-loading="confirmLoading"
    @cancel="handleCancel"
    centered
  >
    <a-form :form="form" @submit="handleSubmit">
      <a-form-item
        label="功能操作"
        :label-col="labelCol"
        :wrapper-col="wrapperCol"
      >
        <a-transfer
          :data-source="allRoles"
          :titles="['待选角色', '已选角色']"
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
          v-decorator="['ids', {}]"
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
export default class UserRoleForm extends Vue {
  visible = false;
  confirmLoading = false;
  public userId = 0;
  public form!: WrappedFormUtils;
  selectedKeys: Array<string> = [];
  targetKeys: Array<string> = [];
  roles: Array<acl.RoleInfo> = [];
  labelCol: FormCol = {
    xs: { span: 24 },
    sm: { span: 5 },
  };
  wrapperCol: FormCol = {
    xs: { span: 24 },
    sm: { span: 17 },
  };

  @Watch("allRoles")
  watchAllActions(): void {
    this.targetKeys =
      this.allRoles.length === 0
        ? []
        : this.allRoles
            .filter((item) => item.selected)
            .map((item) => item.key || "");
  }

  get allRoles(): Partial<acl.RoleInfo>[] {
    if (!this.roles || !this.roles.length) return [];
    return this.roles.map((item) =>
      Object.assign(item, { key: `${item.id}`, title: item.name })
    );
  }

  created(): void {
    this.form = this.$form.createForm(this);
  }

  transferRender(item: { title: string }): string {
    return item.title;
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
    this.userId = id;
    this.loadRoleInfos(id);
  }

  public loadRoleInfos(userId: number): void {
    this.$api.aclApi.user.roles(userId, ({ data: roles }) => {
      this.roles = roles;
    });
  }

  handleSubmit(): void {
    this.confirmLoading = true;
    this.form.validateFields(
      (errors: Array<Error>, values: { ids: Array<number> }) => {
        if (!errors) {
          this.confirmLoading = true;
          this.$api.aclApi.user.grantRoles(
            this.userId,
            values.ids,
            () => {
              this.handleCancel();
              this.$notification.success({
                message: "操作提示",
                description: "用户设置角色成功",
                duration: 3,
                onClose: () => {
                  this.$emit("submited", { id: this.userId });
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
