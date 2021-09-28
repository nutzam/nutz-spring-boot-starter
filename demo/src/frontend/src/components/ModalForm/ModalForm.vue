<template>
  <a-modal
    :width="width"
    :title="title"
    :visible="visible"
    :mask-closable="false"
    :force-render="true"
    @ok="okClick"
    :confirm-loading="confirmLoading"
    @cancel="close"
    centered
  >
    <form-create v-model="fApi" :rule="rule" :option="option"></form-create>
  </a-modal>
</template>
<script lang="ts">
/* eslint-disable @typescript-eslint/no-explicit-any */
/* eslint-disable @typescript-eslint/explicit-module-boundary-types */
import { Api } from "@form-create/ant-design-vue";
import { Component, Prop, Vue } from "vue-property-decorator";
@Component({
  components: {},
})
export default class ModalForm extends Vue {
  @Prop({
    type: String,
    required: false,
    default: "",
  })
  title!: string;

  @Prop({
    type: [String, Number],
    required: false,
    default: 550,
  })
  width!: string | number;

  @Prop({
    type: Array,
    required: true,
  })
  rule!: Array<any>;

  obj = {};

  @Prop({
    type: Object,
    required: false,
    default: () => {
      return {
        form: {
          labelPosition: "right",
          size: "mini",
          hideRequiredAsterisk: false,
          showMessage: true,
          inlineMessage: false,
          labelCol: {
            span: 4,
          },
          wrapperCol: {
            span: 20,
          },
        },
        submitBtn: {
          show: false,
        },
      };
    },
  })
  option!: any;

  fApi: Api | null = null;

  public visible = false;

  public confirmLoading = false;

  /**
   *显示弹窗
   */
  public show(obj: any): void {
    this.visible = true;
    this.obj = Object.assign({}, obj);
    this.fApi?.coverValue(obj || {});
  }

  /**
   * 设置字段隐藏状态
   */
  public hidden(status: boolean, fields: Array<string>): void {
    fields.forEach((field) => {
      this.fApi?.hidden(status, field);
    });
  }
  /**
   * 禁用字段隐藏状态
   */
  public disabled(status: boolean, fields: Array<string>): void {
    fields.forEach((field) => {
      this.fApi?.disabled(status, field);
    });
  }

  okClick(): void {
    this.confirmLoading = true;
    this.fApi?.validate((valid) => {
      if (valid) {
        this.$emit(
          "submited",
          Object.assign(this.obj || {}, this.fApi?.formData())
        );
        this.confirmLoading = false;
        this.visible = false;
      } else {
        this.confirmLoading = false;
      }
    });
  }

  close(): void {
    this.fApi?.resetFields();
    this.confirmLoading = false;
    this.visible = false;
  }
}
</script>
<style lang="less" scoped></style>
