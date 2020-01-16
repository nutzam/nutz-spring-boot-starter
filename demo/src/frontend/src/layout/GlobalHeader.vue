<template>
  <transition name="showHeader">
    <div v-if="visible" class="header-animat">
      <a-layout-header
        v-if="visible"
        style="padding:0;"
        :class="[
          AppModule.fixedHeader && 'ant-header-fixedHeader',
          AppModule.sidebar
            ? 'ant-header-side-opened'
            : 'ant-header-side-closed',
        ]"
      >
        <div v-if="isSideMenu()" class="header">
          <a-icon
            v-if="isMobile()"
            class="trigger"
            :type="collapsed ? 'menu-fold' : 'menu-unfold'"
            @click="toggle"
          />
          <a-icon
            v-else
            class="trigger"
            :type="collapsed ? 'menu-unfold' : 'menu-fold'"
            @click="toggle"
          />
          <header-right-layout />
        </div>
        <div v-else :class="['top-nav-header-index', AppModule.navTheme]">
          <div class="header-index-wide">
            <div class="header-index-left">
              <Logo
                class="top-nav-header"
                :show-title="!isMobile()"
                :title="AppModule.title"
              />
              <sider-menu v-if="!isMobile()" mode="horizontal" />
              <a-icon
                v-else
                class="trigger"
                :type="collapsed ? 'menu-fold' : 'menu-unfold'"
                @click="toggle"
              />
            </div>
            <header-right-layout class="header-index-right" />
          </div>
        </div>
        <!-- <span>{{ $t('globalHeader.message') }}</span>
        <a-date-picker></a-date-picker> -->
      </a-layout-header>
    </div>
  </transition>
</template>

<script lang="ts">
import {Vue, Component, Prop, Emit, Mixins} from 'vue-property-decorator';
import {Getter, Action} from 'vuex-class';
import {Mixin, DeviceMixin} from '@/utils/mixin';
import Logo from './tools/Logo.vue';
import SiderMenu from './menu/SiderMenu.vue';
import HeaderRightLayout from './tools/HeaderRightLayout.vue';

@Component({
  components: {
    Logo,
    SiderMenu,
    HeaderRightLayout,
  },
})
export default class GlobalHeader extends Mixins(Mixin, DeviceMixin) {
  @Prop({default: false}) public collapsed!: boolean;

  private visible: boolean = true;
  @Getter language?: string;

  @Action('ToggleLanguage') toggleLanguage: any;

  @Emit('toggle')
  private toggle() {
    return;
  }
  private localeChange() {
    this.toggleLanguage(this.language === 'enUS' ? 'zhCN' : 'enUS');
  }
}
</script>

<style lang="less">
.header-animat {
  position: relative;
  z-index: 2;
}
.showHeader-enter-active {
  transition: all 0.25s ease;
}
.showHeader-leave-active {
  transition: all 0.5s ease;
}
.showHeader-enter,
.showHeader-leave-to {
  opacity: 0;
}
</style>
