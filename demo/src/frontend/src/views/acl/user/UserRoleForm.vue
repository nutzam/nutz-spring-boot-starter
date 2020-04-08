<template>
  <a-modal
    title="用户角色设置"
    :width="750"
    :visible="visible"
    @ok="handleSubmit"
    :confirm-loading="confirmLoading"
    @cancel="handleCancel"
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
          :render="(item) => item.title"
          @selectChange="handleSelectChange"
          @change="handleActionChange"
          :list-style="{
            width: '200px',
            height: '300px',
          }"
          v-decorator="['ids', {}]"
        />
      </a-form-item>
    </a-form>
  </a-modal>
</template>
<script lang="ts">
import {Component, Prop, Vue, Watch} from 'vue-property-decorator';
import {Role} from '@/types';
@Component({
  components: {},
})
export default class UserPermissionForm extends Vue {
  visible: boolean = false;
  confirmLoading: boolean = false;
  public userId: number = 0;
  public form: any;
  selectedKeys: Array<string> = [];
  targetKeys: Array<string> = [];
  roles: Array<defs.RoleInfo> = [];
  labelCol: any = {
    xs: {span: 24},
    sm: {span: 5},
  };
  wrapperCol: any = {
    xs: {span: 24},
    sm: {span: 17},
  };

  @Watch('allRoles')
  watchAllActions() {
    this.targetKeys = !this.allRoles
      ? []
      : this.allRoles.filter((item) => item.selected).map((item) => item.key);
  }

  get allRoles() {
    if (!this.roles || !this.roles.length) return [];
    return this.roles.map((item) =>
      Object.assign(item, {key: `${item.id}`, title: item.name})
    );
  }

  created() {
    this.form = this.$form.createForm(this);
  }

  filterOption(inputValue: any, option: any) {
    return option.title.indexOf(inputValue) > -1;
  }

  handleSelectChange(
    sourceSelectedKeys: Array<string>,
    targetSelectedKeys: Array<string>
  ) {
    this.selectedKeys = [...sourceSelectedKeys, ...targetSelectedKeys];
  }

  handleActionChange(nextTargetKeys: Array<string>) {
    this.targetKeys = nextTargetKeys;
  }

  public show(id: number) {
    this.form.resetFields();
    this.visible = true;
    this.userId = id;
    this.loadRoleInfos(id);
  }

  public loadRoleInfos(userId: number) {
    this.$api.user.roles(userId, ({data: roles}) => {
      this.roles = roles;
    });
  }

  handleSubmit(e: any) {
    this.confirmLoading = true;
    this.form.validateFields((errors: any, values: any) => {
      if (!errors) {
        this.confirmLoading = true;
        this.$api.user.grantRoles(this.userId, values.ids, (result) => {
          this.confirmLoading = false;
          this.$message.success('用户授权成功', 2, () => {
            this.visible = false;
            this.$emit('submited');
          });
        });
      }
    });
  }
  handleCancel(e: any) {
    this.visible = false;
  }
}
</script>
<style lang="less" scoped></style>
