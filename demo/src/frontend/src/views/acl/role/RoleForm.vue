<template>
  <a-modal
    :title="role && role.id ? '编辑角色' : '添加角色'"
    :visible="visible"
    @ok="handleSubmit"
    :confirm-loading="confirmLoading"
    @cancel="handleCancel"
  >
    <a-form :form="form" @submit="handleSubmit">
      <a-form-item
        label="角色Key"
        :label-col="labelCol"
        :wrapper-col="wrapperCol"
      >
        <a-input
          placeholder="请输入角色Key"
          :disabled="!!(role && role.id)"
          v-decorator="[
            'key',
            {
              rules: [
                {required: true, message: '请输入角色key!'},
                {min: 3, max: 10, message: '角色key长度在 3 到 10 个字符'},
              ],
              initialValue: role && role.key,
            },
          ]"
        />
      </a-form-item>
      <a-form-item
        label="角色名"
        :label-col="labelCol"
        :wrapper-col="wrapperCol"
      >
        <a-input
          placeholder="请输入角色名"
          v-decorator="[
            'name',
            {
              rules: [
                {required: true, message: '请输入角色名!'},
                {min: 2, max: 50, message: '角色名长度在 2 到 50 个字符'},
              ],
              initialValue: role && role.name,
            },
          ]"
        />
      </a-form-item>
      <a-form-item
        label="角色描述"
        :label-col="labelCol"
        :wrapper-col="wrapperCol"
      >
        <a-textarea
          placeholder="请输入角色描述"
          :rows="4"
          v-decorator="[
            'description',
            {
              rules: [
                {min: 2, max: 250, message: '角色描述长度在 2 到 250 个字符'},
              ],
              initialValue: role && role.description,
            },
          ]"
        />
      </a-form-item>
    </a-form>
  </a-modal>
</template>
<script lang="ts">
import {Component, Prop, Vue} from 'vue-property-decorator';
import * as types from '@/store/mutation-types';
@Component({
  components: {},
})
export default class RoleForm extends Vue {
  visible: boolean = false;
  confirmLoading: boolean = false;
  public role: defs.Role | null = null;
  public form: any;
  labelCol: any = {
    xs: {span: 24},
    sm: {span: 5},
  };
  wrapperCol: any = {
    xs: {span: 24},
    sm: {span: 17},
  };
  created() {
    this.form = this.$form.createForm(this);
  }
  public show(role: defs.Role | null) {
    this.form.resetFields();
    this.visible = true;
    this.role = role;
  }

  handleSubmit(e: any) {
    this.form.validateFields((errors: any, values: any) => {
      if (!errors) {
        this.confirmLoading = true;
        if (this.role) {
          this.$api.role.edit(
            Object.assign(values, {id: this.role.id}),
            (result) => {
              this.confirmLoading = false;
              this.$message.success('角色信息更新成功', 2, () => {
                this.visible = false;
                this.$emit('submited');
              });
            }
          );
        } else {
          this.$api.role.add(values, (result) => {
            this.confirmLoading = false;
            this.$message.success('角色添加成功', 2, () => {
              this.visible = false;
              this.$emit('submited');
            });
          });
        }
      }
    });
  }

  handleCancel(e: any) {
    this.visible = false;
  }
}
</script>
<style lang="less" scoped></style>
