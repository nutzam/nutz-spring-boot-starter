<template>
  <div class="logo">
    <router-link :to="{name: 'index'}">
      <logo-svg alt="logo" />
      <h1 v-if="showTitle">{{ title }}</h1>
    </router-link>
  </div>
</template>

<script lang="ts">
import {Vue, Component, Prop} from 'vue-property-decorator';
import LogoSvg from '@/assets/logo.svg?inline';
@Component({
  components: {
    LogoSvg,
  },
})
export default class Logo extends Vue {
  @Prop({default: 'THUNDER'}) public title!: string;
  @Prop({default: true}) public showTitle!: boolean;
}
</script>
