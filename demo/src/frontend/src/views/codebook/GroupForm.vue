<template>
  <a-modal
    :title="group && group.id ? '编辑分组' : '添加分组'"
    :visible="visible"
    @ok="handleSubmit"
    :confirm-loading="confirmLoading"
    @cancel="handleCancel"
  >
    <a-form :form="form" @submit="handleSubmit">
      <a-form-item
        label="分组Key"
        :label-col="labelCol"
        :wrapper-col="wrapperCol"
      >
        <a-input
          placeholder="请输入分组Key"
          :disabled="!!(group && group.id)"
          v-decorator="[
            'key',
            {
              rules: [
                {required: true, message: '请输入分组Key!'},
                {min: 3, max: 50, message: '分组Key长度在 3 到 50 个字符'},
              ],
              initialValue: group && group.key,
            },
          ]"
        />
      </a-form-item>
      <a-form-item
        label="分组名称"
        :label-col="labelCol"
        :wrapper-col="wrapperCol"
      >
        <a-input
          placeholder="请输入分组名称"
          v-decorator="[
            'name',
            {
              rules: [
                {required: true, message: '请输入分组名称!'},
                {min: 2, max: 50, message: '分组名称长度在 2 到 50 个字符'},
              ],
              initialValue: group && group.name,
            },
          ]"
        />
      </a-form-item>
      <a-form-item
        label="分组描述"
        :label-col="labelCol"
        :wrapper-col="wrapperCol"
      >
        <a-textarea
          placeholder="请输入分组描述"
          :rows="4"
          v-decorator="[
            'description',
            {
              rules: [
                {min: 2, max: 250, message: '分组描述长度在 2 到 250 个字符'},
              ],
              initialValue: group && group.description,
            },
          ]"
        />
      </a-form-item>
    </a-form>
  </a-modal>
</template>
<script lang="ts">
import {Component, Prop, Vue} from 'vue-property-decorator';
@Component({
  components: {},
})
export default class GroupForm extends Vue {
  public group: defs.Group | null = null;
  public visible: boolean = false;
  public confirmLoading: boolean = false;
  public form: any;
  public labelCol: any = {
    xs: {span: 24},
    sm: {span: 5},
  };
  public wrapperCol: any = {
    xs: {span: 24},
    sm: {span: 17},
  };
  created() {
    this.form = this.$form.createForm(this);
  }
  public show(group: defs.Group | undefined) {
    this.form.resetFields();
    this.visible = true;
    this.group = group || null;
  }

  handleSubmit() {
    this.form.validateFields((errors: any, values: any) => {
      if (!errors) {
        this.confirmLoading = true;
        if (this.group) {
          this.$api.group.edit(
            Object.assign(values, {id: this.group.id}),
            result => {
              this.confirmLoading = false;
              this.$message.success('分组信息更新成功', 2, () => {
                this.visible = false;
                this.$emit('submited');
              });
            }
          );
        } else {
          this.$api.group.add(values, result => {
            this.confirmLoading = false;
            this.$message.success('分组信息添加成功', 2, () => {
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
