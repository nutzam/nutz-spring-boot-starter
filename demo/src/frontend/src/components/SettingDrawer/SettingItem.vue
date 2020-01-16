<template>
  <div class="setting-drawer-index-item">
    <h3 class="setting-drawer-index-title">{{ title }}</h3>
    <slot></slot>
    <a-divider v-if="divider" />
  </div>
</template>

<script lang="ts">
import {Vue, Component, Prop} from 'vue-property-decorator';
import {Getter, Action} from 'vuex-class';
@Component({
  name: 'SettingItem',
})
export default class SettingItem extends Vue {
  @Prop({default: ''}) public title!: string;
  @Prop({default: false}) public divider!: boolean;
}
</script>
<style lang="less" scoped>
.setting-drawer-index-item {
  margin-bottom: 24px;
  .setting-drawer-index-title {
    font-size: 14px;
    color: rgba(0, 0, 0, 0.85);
    line-height: 22px;
    margin-bottom: 12px;
  }
}
</style>
