<template>
  <a-modal
    :title="user && user.id ? '编辑用户' : '添加用户'"
    :visible="visible"
    @ok="handleSubmit"
    :confirm-loading="confirmLoading"
    @cancel="handleCancel"
  >
    <a-form :form="form" @submit="handleSubmit">
      <a-form-item
        label="用户名"
        :label-col="labelCol"
        :wrapper-col="wrapperCol"
      >
        <a-input
          placeholder="请输入用户名"
          :disabled="!!(user && user.id)"
          v-decorator="[
            'userName',
            {
              rules: [
                {required: true, message: '请输入用户名!'},
                {min: 3, max: 50, message: '用户名长度在 3 到 50 个字符'},
              ],
              initialValue: user && user.userName,
            },
          ]"
        />
      </a-form-item>
      <a-form-item
        v-if="!user || !user.id"
        label="密码"
        :label-col="labelCol"
        :wrapper-col="wrapperCol"
      >
        <a-input-search
          type="password"
          placeholder="请输入密码"
          v-decorator="[
            'pwd',
            {
              rules: [
                {required: true, message: '请输入密码!'},
                {min: 8, max: 16, message: '密码长度在 8 到 16 个字符'},
              ],
            },
          ]"
          @search="onRandom"
        >
          <a-button
            title="点击生成随机密码"
            slot="enterButton"
            type="primary"
            icon="thunderbolt"
          />
        </a-input-search>
      </a-form-item>
      <a-form-item
        label="手机号"
        :label-col="labelCol"
        :wrapper-col="wrapperCol"
      >
        <a-input
          placeholder="请输入手机号码"
          v-decorator="[
            'mobile',
            {
              rules: [
                {required: true, message: '请输入手机号码!'},
                {validator: validators.mobileValidator},
              ],
              initialValue: user && user.mobile,
            },
          ]"
        />
      </a-form-item>
    </a-form>
  </a-modal>
</template>
<script lang="ts">
import {Component, Prop, Vue} from 'vue-property-decorator';
import Utils from '@/utils/util';
import * as types from '@/store/mutation-types';
@Component({
  components: {},
})
export default class UserForm extends Vue {
  visible: boolean = false;
  confirmLoading: boolean = false;
  public user: defs.User | null = null;
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
  public show(user: defs.User | null) {
    this.form.resetFields();
    this.visible = true;
    this.user = user;
  }

  onRandom() {
    var pwd = Utils.randomPassword();
    this.$copyText(pwd).then(() => {
      this.$message.success('密码生成成功,并且已经复制到剪切板', 2, () => {
        this.form.setFieldsValue({
          pwd: pwd,
        });
      });
    });
  }
  handleSubmit(e: any) {
    this.form.validateFields((errors: any, values: any) => {
      if (!errors) {
        this.confirmLoading = true;
        if (this.user) {
          this.$api.user.edit(
            Object.assign(values, {id: this.user.id}),
            (result) => {
              this.confirmLoading = false;
              this.$message.success('用户信息更新成功', 2, () => {
                this.visible = false;
                this.$emit('submited');
              });
            }
          );
        } else {
          this.$api.user.add(values, () => {
            this.confirmLoading = false;
            this.$message.success('用户添加成功', 2, () => {
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
