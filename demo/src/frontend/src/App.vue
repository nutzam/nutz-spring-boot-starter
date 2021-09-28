<template>
  <a-config-provider :locale="locale">
    <div id="app">
      <router-view />
    </div>
  </a-config-provider>
</template>
<script lang="ts">
import { LocaleMessage } from "vue-i18n";
import { Component, Mixins } from "vue-property-decorator";
import { Mixin } from "./utils/mixin";
import Utils from "@/utils/util";
import config from "@/core/config";

@Component({
  components: {},
})
export default class App extends Mixins(Mixin) {
  mounted(): void {
    Utils.setDocumentTitle(config.title);
  }

  get locale(): LocaleMessage {
    console.log(this.$store.getters.language);
    return this.$i18n.getLocaleMessage(this.$store.getters.language).antLocale;
  }
  created(): void {
    document.title = this.AppModule.title;
  }
}
</script>
<style lang="less">
#app {
  height: 100%;
}
.icon-font {
  margin-right: 6px;
  font-size: 24px;
}
/**这个样式很奇特吧 */
.uuid_no_show {
  position: fixed;
  bottom: -10000px;
}
</style>
