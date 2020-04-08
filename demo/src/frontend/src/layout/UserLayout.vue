<template>
  <div id="userLayout" :class="['user-layout-wrapper', AppModule.device]">
    <div class="container">
      <div class="top">
        <div class="header">
          <a href="/">
            <img src="~@/assets/logo.svg" class="logo" alt="logo" />
            <span class="title">{{ AppModule.title }}</span>
          </a>
        </div>
        <div class="desc">
          {{ AppModule.description }}
        </div>
      </div>

      <route-view></route-view>

      <div class="footer">
        <div class="copyright" v-html="copyright"></div>
      </div>
    </div>
  </div>
</template>
<script lang="ts">
import {Prop, Vue, Mixins} from 'vue-property-decorator';
import RouteView from './RouteView.vue';
import {Mixin} from '@/utils/mixin';
import {State} from 'vuex-class';
import Component, {mixins} from 'vue-class-component';
@Component({
  components: {RouteView},
})
export default class UserLayout extends Mixins(Mixin) {
  @State((state) => state.app.copyright) copyright?: string;
  mounted() {
    document.body.classList.add('userLayout');
  }
  beforeDestroy() {
    document.body.classList.remove('userLayout');
  }
}
</script>
<style lang="less" scoped>
#userLayout.user-layout-wrapper {
  height: 100%;

  &.mobile {
    .container {
      .main {
        max-width: 368px;
        width: 98%;
      }
    }
  }

  .container {
    width: 100%;
    min-height: 100%;
    background-image: url(~@/assets/background.png);
    background-size: 100% 100%;
    padding: 110px 0 144px;
    position: relative;

    a {
      text-decoration: none;
    }

    .top {
      text-align: center;

      .header {
        height: 44px;
        line-height: 44px;

        .badge {
          position: absolute;
          display: inline-block;
          line-height: 1;
          vertical-align: middle;
          margin-left: -12px;
          margin-top: -10px;
          opacity: 0.8;
        }

        .logo {
          height: 44px;
          vertical-align: top;
          margin-right: 16px;
          border-style: none;
        }

        .title {
          font-size: 33px;
          color: rgba(256, 256, 256, 0.85);
          font-family: Avenir, 'Helvetica Neue', Arial, Helvetica, sans-serif;
          font-weight: 600;
          position: relative;
          top: 2px;
        }
      }
      .desc {
        font-size: 24px;
        color: rgba(255, 255, 255, 0.65);
        margin-top: 12px;
        margin-bottom: 40px;
      }
    }

    .main {
      min-width: 260px;
      width: 368px;
      margin: 0 auto;
    }

    .footer {
      position: absolute;
      width: 100%;
      bottom: 0;
      padding: 0 16px;
      margin: 48px 0 24px;
      text-align: center;

      .links {
        margin-bottom: 8px;
        font-size: 14px;
        a {
          color: rgba(256, 256, 256, 0.45);
          transition: all 0.3s;
          &:not(:last-child) {
            margin-right: 40px;
          }
        }
      }
      .copyright {
        color: rgba(256, 256, 256, 0.45);
        font-size: 14px;
      }
    }
  }
}
</style>
