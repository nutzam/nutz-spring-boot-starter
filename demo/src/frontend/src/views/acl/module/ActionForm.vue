<template>
  <a-modal
    :title="action && action.id ? '编辑操作' : '添加操作'"
    :visible="visible"
    @ok="handleSubmit"
    :confirm-loading="confirmLoading"
    @cancel="handleCancel"
  >
    <a-form :form="form" @submit="handleSubmit">
      <a-form-item
        label="操作Key"
        :label-col="labelCol"
        :wrapper-col="wrapperCol"
      >
        <a-input
          placeholder="请输入操作Key"
          :disabled="!!(action && action.id)"
          v-decorator="[
            'key',
            {
              rules: [
                {required: true, message: '请输入操作key!'},
                {min: 3, max: 10, message: '操作key长度在 3 到 10 个字符'},
              ],
              initialValue: action && action.key,
            },
          ]"
        />
      </a-form-item>
      <a-form-item
        label="操作名"
        :label-col="labelCol"
        :wrapper-col="wrapperCol"
      >
        <a-input
          placeholder="请输入操作名"
          v-decorator="[
            'name',
            {
              rules: [
                {required: true, message: '请输入操作名!'},
                {min: 2, max: 50, message: '操作名长度在 2 到 50 个字符'},
              ],
              initialValue: action && action.name,
            },
          ]"
        />
      </a-form-item>
    </a-form>
  </a-modal>
</template>
<script lang="ts">
import {Component, Prop, Vue} from 'vue-property-decorator';
import {Action} from '@/types';
import * as types from '@/store/mutation-types';
@Component({
  components: {},
})
export default class ActionForm extends Vue {
  visible: boolean = false;
  confirmLoading: boolean = false;
  public action: defs.Action | null = null;
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
  public show(action: defs.Action | null) {
    this.form.resetFields();
    this.visible = true;
    this.action = action;
  }

  handleSubmit(e: any) {
    this.form.validateFields((errors: any, values: any) => {
      if (!errors) {
        this.confirmLoading = true;
        if (this.action && this.action.id) {
          this.$api.action.edit(
            Object.assign(values, {id: this.action.id}),
            (result) => {
              this.confirmLoading = false;
              this.$message.success('操作信息更新成功', 2, () => {
                this.visible = false;
                this.$emit('submited');
              });
            }
          );
        } else {
          this.$api.action.add(Object.assign(this.action, values), (result) => {
            this.confirmLoading = false;
            this.$message.success('操作添加成功', 2, () => {
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
