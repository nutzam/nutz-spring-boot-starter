<template>
  <div>
    <router-view></router-view>
    <vue-particles
      color="#6495ED"
      :particle-opacity="0.7"
      :particles-number="80"
      shape-type="circle"
      :particle-size="4"
      lines-color="#6495ED"
      :lines-width="1"
      :line-linked="true"
      :line-opacity="0.6"
      :lines-distance="150"
      :move-speed="3"
      :hover-effect="true"
      hover-mode="grab"
      :click-effect="true"
      click-mode="push"
    >
    </vue-particles>

    <BackgroundAnimation />
  </div>
</template>
<script lang="ts">
import { Vue } from "vue-property-decorator";
import BackgroundAnimation from "@/components/BackgroundAnimation/BackgroundAnimation.vue";
import RouteView from "./RouteView.vue";
import Component from "vue-class-component";
@Component({
  components: { RouteView, BackgroundAnimation },
})
export default class UserLayout extends Vue {}
</script>
<style lang="less" scoped>
.copy-right {
  position: fixed;
  text-align: center;
  color: #4b91c3;
  bottom: 20px;
  left: 50%;
}
</style>
