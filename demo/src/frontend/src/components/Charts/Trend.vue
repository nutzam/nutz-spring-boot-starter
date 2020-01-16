<template>
  <div class="chart-trend">
    {{ term }}
    <slot name="term"></slot>
    <span>{{ rate }}%</span>
    <span :class="['trend-icon', trend]">
      <a-icon :type="'caret-' + trend"
    /></span>
  </div>
</template>
<script lang="ts">
import {Component, Prop, Vue} from 'vue-property-decorator';
@Component({
  components: {},
})
export default class Trend extends Vue {
  @Prop({
    type: String,
    default: '',
    required: false,
  })
  private term!: string;

  @Prop({
    type: Number,
    default: null,
  })
  private percentage!: Number;

  @Prop({type: Boolean, default: null})
  private type!: boolean;

  @Prop({
    type: Number,
    default: 0,
  })
  private target!: number;

  @Prop({
    type: Number,
    default: 0,
  })
  private value!: number;

  @Prop({
    type: Number,
    default: 2,
  })
  private fixed!: number;

  public trend = (this.type && 'up') || 'down';
  public rate: Number | string = this.percentage;

  created() {
    const type = this.type === null ? this.value >= this.target : this.type;
    this.trend = type ? 'up' : 'down';
    this.rate = (this.percentage === null
      ? (Math.abs(this.value - this.target) * 100) / this.target
      : this.percentage
    ).toFixed(this.fixed);
  }
}
</script>
<style lang="less" scoped>
.chart-trend {
  display: inline-block;
  font-size: 14px;
  line-height: 22px;

  .trend-icon {
    font-size: 12px;

    &.up,
    &.down {
      margin-left: 4px;
      position: relative;
      top: 1px;

      i {
        font-size: 12px;
        transform: scale(0.83);
      }
    }

    &.up {
      color: #f5222d;
    }
    &.down {
      color: #52c41a;
      top: -1px;
    }
  }
}
</style>
