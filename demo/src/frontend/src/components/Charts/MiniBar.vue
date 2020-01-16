<template>
  <div class="antv-chart-mini">
    <div class="chart-wrapper" :style="{height: 46}">
      <v-chart
        :force-fit="true"
        :height="height"
        :data="data"
        :padding="[36, 5, 18, 5]"
      >
        <v-tooltip />
        <v-bar position="x*y" />
      </v-chart>
    </div>
  </div>
</template>
<script lang="ts">
import moment from 'moment';
import {Component, Prop, Vue} from 'vue-property-decorator';
@Component({
  components: {},
})
export default class MiniBar extends Vue {
  public data: Array<any> = [];
  public tooltip: Array<any> = [
    'x*y',
    (x: number, y: number) => ({
      name: x,
      value: y,
    }),
  ];
  public scale: Array<any> = [
    {
      dataKey: 'x',
      min: 2,
    },
    {
      dataKey: 'y',
      title: '时间',
      min: 1,
      max: 30,
    },
  ];
  public height: number = 100;

  created() {
    const beginDay = new Date().getTime();
    for (let i = 0; i < 10; i++) {
      for (let i = 0; i < 10; i++) {
        this.data.push({
          x: moment(new Date(beginDay + 1000 * 60 * 60 * 24 * i)).format(
            'YYYY-MM-DD'
          ),
          y: Math.round(Math.random() * 10),
        });
      }
    }
  }
}
</script>
<style lang="less" scoped>
.antv-chart-mini {
  position: relative;
  width: 100%;

  .chart-wrapper {
    position: absolute;
    bottom: -28px;
    width: 100%;
  }
}
</style>
