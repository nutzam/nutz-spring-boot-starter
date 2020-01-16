<template>
  <keep-alive v-if="keep">
    <router-view />
  </keep-alive>
  <router-view v-else />
</template>
<script lang="ts">
import {Component, Prop, Vue} from 'vue-property-decorator';
@Component({
  components: {},
})
export default class RouteView extends Vue {
  @Prop({type: Boolean, default: true})
  private keepAlive!: boolean;
  get keep() {
    return (
      this.keepAlive ||
      this.$route.meta.keepAlive ||
      this.$store.getters.multiTab
    );
  }
}
</script>
<style lang="less" scoped></style>
