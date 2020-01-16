<template>
  <a-breadcrumb class="breadcrumb">
    <a-breadcrumb-item v-for="item in breadList" :key="item.name">
      <router-link
        v-if="item.name != name"
        :to="{path: item.path === '' ? '/' : item.path}"
        >{{ $t(`menu.${item.meta.title}`) }}</router-link
      >
      <span v-else>{{ $t(`menu.${item.meta.title}`) }}</span>
    </a-breadcrumb-item>
  </a-breadcrumb>
</template>
<script lang="ts">
import {Component, Prop, Vue, Watch} from 'vue-property-decorator';
@Component({
  components: {},
})
export default class Breadcrumb extends Vue {
  public name: string | undefined = '';
  public breadList: Array<any> = [];

  created() {
    this.getBreadcrumb();
  }

  getBreadcrumb() {
    this.breadList = [];
    this.name = this.$route.name;
    this.$route.matched.forEach(item => {
      this.breadList.push(item);
    });
  }

  @Watch('$route')
  watchRoute() {
    this.getBreadcrumb();
  }
}
</script>
<style lang="stylus" scoped></style>
