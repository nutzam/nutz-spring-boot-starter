<template>
  <div :style="{padding: '0 0 32px 32px'}">
    <h4 :style="{marginBottom: '20px'}">{{ title }}</h4>
    <v-chart
      height="254"
      :data="data"
      :force-fit="true"
      :padding="['auto', 'auto', '40', '50']"
    >
      <v-tooltip />
      <v-axis />
      <v-bar position="x*y" />
    </v-chart>
  </div>
</template>
<script lang="ts">
import {Component, Prop, Vue} from 'vue-property-decorator';
@Component({
  components: {},
})
export default class Bar extends Vue {
  @Prop({
    type: String,
    default: '',
  })
  private title!: string;

  @Prop({
    type: Array,
    default: () => {
      return [];
    },
  })
  private data!: Array<any>;

  @Prop({
    type: Array,
    default: () => {
      return [
        {
          dataKey: 'x',
          min: 2,
        },
        {
          dataKey: 'y',
          title: '时间',
          min: 1,
          max: 22,
        },
      ];
    },
  })
  private scale!: Array<any>;

  @Prop({
    type: Array,
    default: () => {
      return [
        'x*y',
        (x: number, y: number) => ({
          name: x,
          value: y,
        }),
      ];
    },
  })
  private tooltip!: Array<any>;
}
</script>
<style lang="less" scoped></style>
