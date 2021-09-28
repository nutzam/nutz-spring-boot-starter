<template>
  <div :class="wrpCls">
    <avatar-dropdown :class="prefixCls" />
    <select-lang :class="prefixCls" />
  </div>
</template>
<script lang="ts">
import { Component, Prop, Vue } from "vue-property-decorator";
import AvatarDropdown from "./AvatarDropdown.vue";
import SelectLang from "@/components/SelectLang/SelectLang.vue";

@Component({
  components: { AvatarDropdown, SelectLang },
})
export default class RightContent extends Vue {
  @Prop({
    type: String,
    default: "ant-pro-global-header-index-action",
  })
  private prefixCls!: string;

  @Prop({
    type: Boolean,
    default: () => false,
  })
  private isMobile!: boolean;

  @Prop({
    type: Boolean,
    required: true,
  })
  private topMenu!: boolean;

  @Prop({
    type: String,
    required: true,
  })
  private theme!: string;

  get wrpCls(): { "ant-pro-global-header-index-right": boolean } {
    return {
      "ant-pro-global-header-index-right": true,
      [`ant-pro-global-header-index-${
        this.isMobile || !this.topMenu ? "light" : this.theme
      }`]: true,
    };
  }
}
</script>
<style lang="less" scoped></style>
