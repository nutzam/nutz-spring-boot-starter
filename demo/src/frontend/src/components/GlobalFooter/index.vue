<template>
  <global-footer class="footer custom-render">
    <template #links> </template>
    <template #copyright>
      <a
        href="https://github.com/vueComponent"
        target="_blank"
        v-html="AppModule.copyright"
      ></a>
    </template>
  </global-footer>
</template>
<script lang="ts">
import { Component, Mixins } from "vue-property-decorator";
import { GlobalFooter } from "@ant-design-vue/pro-layout";
import { Mixin } from "@/utils/mixin";
@Component({
  components: { GlobalFooter },
})
export default class MyGlobalFooter extends Mixins(Mixin) {}
</script>
<style lang="less" scoped></style>
