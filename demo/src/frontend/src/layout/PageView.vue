<template>
  <div
    :style="
      !$route.meta.hiddenHeaderContent ? 'margin: -24px -24px 0px;' : null
    "
  >
    <page-header
      v-if="!$route.meta.hiddenHeaderContent"
      :title="pageTitle"
      :logo="logo"
      :avatar="avatar"
    >
      <slot slot="action" name="action"></slot>
      <slot slot="content" name="headerContent"></slot>
      <div slot="content" v-if="!this.$slots.headerContent && description">
        <p style="font-size: 14px;color: rgba(0,0,0,.65)">{{ description }}</p>
        <div class="link">
          <template v-for="(link, index) in linkList">
            <a :key="index" :href="link.href">
              <a-icon :type="link.icon" />
              <span>{{ link.title }}</span>
            </a>
          </template>
        </div>
      </div>
      <slot slot="extra" name="extra">
        <div class="extra-img">
          <img v-if="typeof extraImage !== 'undefined'" :src="extraImage" />
        </div>
      </slot>
      <div slot="pageMenu">
        <div class="page-menu-search" v-if="search">
          <a-input-search
            style="width: 80%; max-width: 522px;"
            placeholder="请输入..."
            size="large"
            enter-button="搜索"
          />
        </div>
        <div class="page-menu-tabs" v-if="tabs && tabs.items">
          <!-- @change="callback" :activeKey="activeKey" -->
          <a-tabs
            :tab-bar-style="{margin: 0}"
            :active-key="tabs.active()"
            @change="tabs.callback"
          >
            <a-tab-pane
              v-for="item in tabs.items"
              :tab="item.title"
              :key="item.key"
            ></a-tab-pane>
          </a-tabs>
        </div>
      </div>
    </page-header>
    <div class="content">
      <div class="page-header-index-wide">
        <slot>
          <!-- keep-alive  -->
          <a-card>
            <keep-alive v-if="AppModule.multiTab">
              <router-view ref="content" />
            </keep-alive>
            <router-view v-else ref="content" />
          </a-card>
        </slot>
      </div>
    </div>
  </div>
</template>
<script lang="ts">
import {Component, Prop, Vue, Mixins, Watch} from 'vue-property-decorator';
import PageHeader from '@/components/PageHeader';
import {Mixin} from '../utils/mixin';
import {Getter} from 'vuex-class';

@Component({
  components: {PageHeader},
})
export default class PageView extends Mixins(Mixin) {
  @Getter language?: string;

  @Prop({
    type: String,
    default: null,
  })
  private avatar!: string;

  @Prop({type: [String, Boolean], default: true})
  private title!: string;

  @Prop({type: String, default: null})
  private logo!: string;

  @Prop({
    type: Object,
    default: null,
  })
  private directTabs!: string;

  public pageTitle: string | null = null;
  public description: string | null = null;
  public linkList: Array<any> = [];
  public extraImage: string = '';
  public search: boolean = false;
  public tabs: any = {};

  mounted() {
    this.tabs = this.directTabs;
    this.getPageMeta();
  }

  updated() {
    this.getPageMeta();
  }
  @Watch('language')
  watchLanguage() {
    this.getPageMeta();
  }

  getPageMeta() {
    this.pageTitle =
      typeof this.title === 'string' || !this.title
        ? this.title
        : (this.$t(`menu.${this.$route.meta.title}`) as string);

    const content = this.$refs.content as any;
    if (content) {
      if (content.pageMeta) {
        Object.assign(this, content.pageMeta);
      } else {
        this.description = content.description;
        this.linkList = content.linkList;
        this.extraImage = content.extraImage;
        this.search = content.search === true;
        this.tabs = content.tabs;
      }
    }
  }
}
</script>
<style lang="less" scoped>
.content {
  margin: 24px 24px 0;
  .link {
    margin-top: 16px;
    &:not(:empty) {
      margin-bottom: 16px;
    }
    a {
      margin-right: 32px;
      height: 24px;
      line-height: 24px;
      display: inline-block;
      i {
        font-size: 24px;
        margin-right: 8px;
        vertical-align: middle;
      }
      span {
        height: 24px;
        line-height: 24px;
        display: inline-block;
        vertical-align: middle;
      }
    }
  }
}
.page-menu-search {
  text-align: center;
  margin-bottom: 16px;
}
.page-menu-tabs {
  margin-top: 48px;
}

.extra-img {
  margin-top: -60px;
  text-align: center;
  width: 195px;

  img {
    width: 100%;
  }
}

.mobile {
  .extra-img {
    margin-top: 0;
    text-align: center;
    width: 96px;

    img {
      width: 100%;
    }
  }
}
</style>
