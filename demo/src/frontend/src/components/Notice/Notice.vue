<template>
  <div class="tvue-notice-wrapper">
    <a-popover
      v-model="visible"
      trigger="click"
      placement="bottomRight"
      overlay-class-name="header-notice-wrapper"
      :auto-adjust-overflow="true"
      :arrow-point-at-center="true"
      :overlay-style="{width: '350px'}"
    >
      <template slot="content">
        <a-spin :spinning="loading">
          <a-tabs>
            <a-tab-pane :tab="$t('notice.notice')" key="1">
              <a-list>
                <a-list-item>
                  <a-list-item-meta
                    title="你收到了 14 份新周报"
                    description="一年前"
                  >
                    <a-avatar
                      style="background-color: white"
                      slot="avatar"
                      src="https://gw.alipayobjects.com/zos/rmsportal/ThXAXghbEsBCCSDihZxY.png"
                    />
                  </a-list-item-meta>
                </a-list-item>
                <a-list-item>
                  <a-list-item-meta
                    title="你推荐的 曲妮妮 已通过第三轮面试"
                    description="一年前"
                  >
                    <a-avatar
                      style="background-color: white"
                      slot="avatar"
                      src="https://gw.alipayobjects.com/zos/rmsportal/OKJXDXrmkNshAMvwtvhu.png"
                    />
                  </a-list-item-meta>
                </a-list-item>
                <a-list-item>
                  <a-list-item-meta
                    title="这种模板可以区分多种通知类型"
                    description="一年前"
                  >
                    <a-avatar
                      style="background-color: white"
                      slot="avatar"
                      src="https://gw.alipayobjects.com/zos/rmsportal/kISTdvpyTAhtGxpovNWd.png"
                    />
                  </a-list-item-meta>
                </a-list-item>
                <div class="tvue-components-notice-icon-notice-list-bottomBar">
                  <div class="bottomBar__left">清空 通知</div>
                  <div class="bottomBar__right">查看更多</div>
                </div>
              </a-list>
            </a-tab-pane>
            <a-tab-pane :tab="$t('notice.message')" key="2">123</a-tab-pane>
            <a-tab-pane :tab="$t('notice.todo')" key="3">123</a-tab-pane>
          </a-tabs>
        </a-spin>
      </template>
      <span @click="fetchNotice" class="header-notice">
        <a-badge count="12">
          <a-icon style="font-size: 16px; padding: 4px" type="bell" />
        </a-badge>
      </span>
    </a-popover>
  </div>
</template>

<script lang="ts">
// import { getUser } from "@/api/user";
import {Vue, Component} from 'vue-property-decorator';

@Component({
  name: 'Notice',
})
export default class Notice extends Vue {
  private visible: boolean = false;
  private loading: boolean = false;
  private mounted() {
    setTimeout(() => {
      // getUser().then((res: any) => {
      //   console.log(res);
      // });
    }, 6000);
  }
  private fetchNotice() {
    if (!this.visible) {
      this.loading = true;
      setTimeout(() => {
        this.loading = false;
      }, 2000);
    } else {
      this.loading = false;
    }
    this.visible = !this.visible;
  }
}
</script>
<style lang="css" scoped>
.header-notice-wrapper {
  top: 50px !important;
}
.ant-popover-inner-content {
  padding: 0 !important;
}
.ant-tabs-nav-scroll {
  display: flex;
  justify-content: center;
}
.ant-list-item {
  padding-left: 22px !important;
  padding-right: 22px !important;
}
</style>
<style lang="less" scoped>
.tvue-components-notice-icon-notice-list-bottomBar {
  display: flex;
  flex: 2;
  justify-content: space-around;
  height: 46px;
  color: rgba(0, 0, 0, 0.65);
  line-height: 46px;
  text-align: center;
  border-radius: 0 0 4px 4px;
  transition: all 0.3s;
  .bottomBar__left {
    flex: 1;
    border-right: 1px solid #e8e8e8;
  }
  .bottomBar__right {
    flex: 1;
  }
}
.header-notice {
  display: inline-block;
  transition: all 0.3s;
  span {
    vertical-align: initial;
  }
}
</style>
