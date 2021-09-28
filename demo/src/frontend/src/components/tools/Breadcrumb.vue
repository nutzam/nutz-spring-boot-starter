<template>
  <a-breadcrumb class="breadcrumb">
    <a-breadcrumb-item v-for="item in breadList" :key="item.name">
      <router-link
        v-if="item.name != name"
        :to="{ path: item.path === '' ? '/' : item.path }"
        >{{ $t(`${item.meta.title}`) }}</router-link
      >
      <span v-else>{{ $t(`${item.meta.title}`) }}</span>
    </a-breadcrumb-item>
  </a-breadcrumb>
</template>
<script lang="ts">
/* eslint-disable @typescript-eslint/no-explicit-any */
import { Component, Vue, Watch } from "vue-property-decorator";
@Component({
  components: {},
})
export default class Breadcrumb extends Vue {
  public name: string | undefined = "";
  public breadList: Array<any> = [];

  created(): void {
    this.getBreadcrumb();
  }

  getBreadcrumb(): void {
    this.breadList = [];
    this.name = this.$route.name as string;
    this.$route.matched.forEach((item) => {
      if (item.path !== "/index") this.breadList.push(item);
    });
  }

  @Watch("$route")
  watchRoute(): void {
    this.getBreadcrumb();
  }
}
</script>
<style lang="stylus" scoped></style>
