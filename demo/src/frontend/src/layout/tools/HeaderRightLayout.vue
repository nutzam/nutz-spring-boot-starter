<template>
  <div class="header-right-layout-wrapper">
    <div class="content-box">
      <a href="https://vue.ant.design/" target="_blank">
        <span class="action">
          <a-icon type="question-circle-o" class="icon"></a-icon>
        </span>
      </a>
      <Notice class="action" />
      <a-dropdown>
        <span class="action ant-dropdown-link user-dropdown-menu">
          <a-avatar
            class="avatar"
            style="background-color: #ffffff00;"
            size="small"
            :src="toImgUrl(UserModule.avatar)"
          />
          <span>{{ UserModule.name || UserModule.userName }}</span>
        </span>
        <a-menu
          slot="overlay"
          class="user-dropdown-menu-wrapper"
          :style="contentWith"
        >
          <a-menu-item key="0" :style="contentWith">
            <router-link :to="{name: 'center'}">
              <a-icon type="user" />
              <span>{{ $t('header.userCenter') }}</span>
            </router-link>
          </a-menu-item>
          <a-menu-item key="1" :style="contentWith">
            <router-link :to="{name: 'settings'}">
              <a-icon type="setting" />
              <span>{{ $t('header.settings') }}</span>
            </router-link>
          </a-menu-item>
          <a-menu-divider />
          <a-menu-item key="3" :style="contentWith">
            <a href="javascript:;" @click="doLogout">
              <a-icon type="logout" />
              <span>{{ $t('header.logout') }}</span>
            </a>
          </a-menu-item>
        </a-menu>
      </a-dropdown>
      <a-dropdown>
        <span class="action ant-dropdown-link user-dropdown-menu">
          <a-icon type="global" class="icon" />
        </span>
        <a-menu
          class="user-dropdown-menu-wrapper"
          slot="overlay"
          @click="localeChange"
          :selected-keys="[language]"
          :style="contentWith"
        >
          <a-menu-item key="zhCN" :style="contentWith">
            <span role="img" aria-label="简体中文">🇨🇳</span>&nbsp;简体中文
          </a-menu-item>
          <a-menu-item key="enUS" :style="contentWith">
            <span role="img" aria-label="English">🇬🇧</span>&nbsp;English
          </a-menu-item>
        </a-menu>
      </a-dropdown>
    </div>
  </div>
</template>

<script lang="ts">
import {Vue, Component, Mixins} from 'vue-property-decorator';
import {Getter, Action, Mutation} from 'vuex-class';

import Notice from '@/components/Notice';
import {DeviceMixin} from '@/utils/mixin';
import Utils from '@/utils/util';
import {findLast, throttle} from 'lodash';

@Component({
  name: 'HeaderRightLayout',
  components: {
    Notice,
  },
})
export default class HeaderRightLayout extends Mixins(DeviceMixin) {
  private clientWidth: number = 0;

  @Mutation('logout') logout?: () => void;

  @Getter language?: string;

  @Action('ToggleLanguage') toggleLanguage?: (lan: string) => any;

  // 如果为手机端下拉菜单则为屏幕宽度
  get contentWith() {
    return this.isMobile() ? `width:${this.clientWidth}px;` : '';
  }

  /**
   * doLogout
   */
  public doLogout() {
    let confirm = this.$t('header.confirm');
    let confirmMessage = this.$t('header.confirmMessage');
    let ok = this.$t('header.ok') as string;
    let cancle = this.$t('header.cancle') as string;
    this.$confirm({
      title: confirm,
      content: confirmMessage,
      okText: ok,
      okType: 'danger',
      cancelText: cancle,
      onOk: () => {
        this.logout && this.logout();
        location.reload();
      },
    });
  }

  private mounted() {
    this.clientWidth = window.innerWidth;
    window.onresize = throttle(() => {
      this.clientWidth = window.innerWidth;
    }, 1000);
  }

  private localeChange() {
    return this.$message
      .loading(this.$t('globalHeader.message'), 1)
      .then(async () => {
        this.$i18n.locale = this.language === 'enUS' ? 'zhCN' : 'enUS';
        this.toggleLanguage &&
          (await this.toggleLanguage(
            this.language === 'enUS' ? 'zhCN' : 'enUS'
          ));
        const record = findLast(
          this.$route.matched,
          (record: any) => record.meta.title
        );
        let title: string;
        if (record) {
          title = `${record.meta.localeTitle} - THUNDER`;
        } else {
          title = 'THUNDER';
        }
        Utils.setDocumentTitle(title);
      });
  }
}
</script>
