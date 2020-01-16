<template>
  <div :class="[prefixCls]">
    <slot name="subtitle">
      <div :class="[`${prefixCls}-subtitle`]">
        {{ typeof subTitle === 'string' ? subTitle : subTitle() }}
      </div>
    </slot>
    <div class="number-info-value">
      <span>{{ total }}</span>
      <span class="sub-total">
        {{ subTotal }}
        <a-icon :type="`caret-${status}`" />
      </span>
    </div>
  </div>
</template>
<script lang="ts">
import {Component, Prop, Vue} from 'vue-property-decorator';
@Component({
  components: {},
})
export default class NumberInfo extends Vue {
  @Prop({
    type: String,
    default: 'ant-pro-number-info',
  })
  private prefixCls!: string;

  @Prop({
    type: Number,
    required: true,
  })
  private total!: Number;

  @Prop({
    type: Number,
    required: true,
  })
  private subTotal!: Number;

  @Prop({
    type: [String, Function],
    default: '',
  })
  private subTitle!: string;

  @Prop({
    type: String,
    default: 'up',
  })
  private status!: string;
}
</script>
<style lang="less" scoped></style>
