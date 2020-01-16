<template>
  <a-locale-provider :locale="locale">
    <div id="app">
      <router-view />
    </div>
  </a-locale-provider>
</template>
<script lang="ts">
import {Component, Prop, Vue, Mixins, Watch} from 'vue-property-decorator';
import {AppDeviceEnquireMixin, Mixin} from '@/utils/mixin';
import {Getter} from 'vuex-class';
import {Initializer} from './core';
import zhCN from 'ant-design-vue/lib/locale-provider/zh_CN';
import enUS from 'ant-design-vue/lib/locale-provider/en_US';
import moment from 'moment';
import 'moment/locale/zh-cn';
import {deviceEnquire, DEVICE_TYPE} from '@/utils/device';
import {check} from './utils/auth';

@Component({
  components: {},
})
export default class App extends Mixins(AppDeviceEnquireMixin) {
  @Getter language?: string;

  get locale() {
    moment.locale(this.language === 'enUS' ? 'en' : 'zh-cn');
    this.$i18n.locale = this.language === 'enUS' ? 'enUS' : 'zhCN';
    return this.language === 'enUS' ? enUS : zhCN;
  }
  @Getter('token') token?: string;
  beforeCreate() {
    Initializer(false);
  }

  @Watch('$route.path')
  routePathChange(val: any) {
    const isUserPage = val.indexOf('/user') == 0 || val.indexOf('/test') == 0;
    const token = this.token;
    if (!token && !isUserPage) {
      this.$router.push({path: '/user'});
    } else if (token) {
      this.$api.auth.check(
        (data: any) => {
          if (isUserPage) {
            console.log(this.$router);
            this.$router.push({path: '/index'});
          }
        },
        (error: string) => {
          if (!isUserPage) {
            this.$router.push({path: '/user'});
          }
        }
      );
    }
  }
}
</script>
<style lang="less">
#app {
  height: 100%;
}
/**这个样式很奇特吧 */
.uuid_no_show {
  position: fixed;
  bottom: -10000px;
}
</style>
