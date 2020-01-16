<template>
  <div :class="prefixCls">
    <div class="chart-wrapper" :style="{height: 46}">
      <v-chart
        :force-fit="true"
        :height="100"
        :data="dataSource"
        :scale="scale"
        :padding="[36, 0, 18, 0]"
      >
        <v-tooltip />
        <v-smooth-line position="x*y" :size="2" />
        <v-smooth-area position="x*y" />
      </v-chart>
    </div>
  </div>
</template>
<script lang="ts">
import {Component, Prop, Vue} from 'vue-property-decorator';
@Component({
  components: {},
})
export default class MiniSmoothArea extends Vue {
  @Prop({
    type: String,
    default: 'ant-pro-smooth-area',
  })
  private prefixCls!: string;

  @Prop({
    type: [Object, Array],
    required: true,
  })
  private scale!: Object | Array<any>;

  @Prop({
    type: Array,
    required: true,
  })
  private dataSource!: Array<any>;

  public height: number = 100;
}
</script>
<style lang="less" scoped>
@import '../index';

@smoothArea-prefix-cls: ~'@{ant-pro-prefix}-smooth-area';

.@{smoothArea-prefix-cls} {
  position: relative;
  width: 100%;

  .chart-wrapper {
    position: absolute;
    bottom: -28px;
    width: 100%;
  }
}
</style>
