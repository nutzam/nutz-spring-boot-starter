<template>
  <a-modal
    :title="module && module.id ? '编辑模块' : '添加模块'"
    :visible="visible"
    @ok="handleSubmit"
    :confirm-loading="confirmLoading"
    @cancel="handleCancel"
  >
    <a-form :form="form" @submit="handleSubmit">
      <a-form-item
        label="模块Key"
        :label-col="labelCol"
        :wrapper-col="wrapperCol"
      >
        <a-input
          placeholder="请输入模块Key"
          :disabled="!!(module && module.id)"
          v-decorator="[
            'key',
            {
              rules: [
                {required: true, message: '请输入模块key!'},
                {min: 3, max: 10, message: '模块key长度在 3 到 10 个字符'},
              ],
              initialValue: module && module.key,
            },
          ]"
        />
      </a-form-item>
      <a-form-item
        label="模块名"
        :label-col="labelCol"
        :wrapper-col="wrapperCol"
      >
        <a-input
          placeholder="请输入模块名"
          v-decorator="[
            'name',
            {
              rules: [
                {required: true, message: '请输入模块名!'},
                {min: 2, max: 50, message: '模块名长度在 2 到 50 个字符'},
              ],
              initialValue: module && module.name,
            },
          ]"
        />
      </a-form-item>
      <a-form-item
        label="模块描述"
        :label-col="labelCol"
        :wrapper-col="wrapperCol"
      >
        <a-textarea
          placeholder="请输入模块描述"
          :rows="4"
          v-decorator="[
            'descr',
            {
              rules: [
                {min: 2, max: 250, message: '模块描述长度在 2 到 250 个字符'},
              ],
              initialValue: module && module.descr,
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
export default class ModuleForm extends Vue {
  visible: boolean = false;
  confirmLoading: boolean = false;
  public module: defs.Module | null = null;
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
  public show(module: defs.Module | null) {
    this.form.resetFields();
    this.visible = true;
    this.module = module;
  }

  handleSubmit(e: any) {
    this.form.validateFields((errors: any, values: any) => {
      if (!errors) {
        this.confirmLoading = true;
        if (this.module) {
          this.$api.module.edit(
            Object.assign(values, {id: this.module.id}),
            result => {
              this.confirmLoading = false;
              this.$message.success('模块信息更新成功', 2, () => {
                this.visible = false;
                this.$emit('submited');
              });
            }
          );
        } else {
          this.$api.module.add(values, result => {
            this.confirmLoading = false;
            this.$message.success('模块添加成功', 2, () => {
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
