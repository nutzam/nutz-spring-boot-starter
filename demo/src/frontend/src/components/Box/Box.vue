<template>
  <div :style="style">
    <slot></slot>
  </div>
</template>
<script lang="ts">
import {Component, Prop, Vue} from 'vue-property-decorator';
@Component({
  components: {},
})
export default class Box extends Vue {
  @Prop({required: false, default: 0, type: Number}) private left!: number;
  @Prop({required: false, default: 0, type: Number}) private right!: number;
  @Prop({required: false, default: 0, type: Number}) private top!: number;
  @Prop({required: false, default: 0, type: Number}) private bottom!: number;
  get style() {
    return {
      margin: `${this.top}px ${this.right}px ${this.bottom}px ${this.left}px`,
    };
  }
}
</script>
<style lang="stylus" scoped></style>
