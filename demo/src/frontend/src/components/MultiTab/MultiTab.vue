<template>
  <div class="ant-pro-multi-tab">
    <div class="ant-pro-multi-tab-wrapper">
      <a-tabs
        hide-add
        type="editable-card"
        v-model="activeKey"
        :tab-bar-style="tabBarStyle"
        @change="handleChange"
        @edit="onEdit"
      >
        <a-tab-pane
          v-for="page in pages"
          :key="page.fullPath"
          style="height: 0"
          :closable="pages.length > 1"
        >
          <a-dropdown slot="tab" :trigger="['contextmenu']">
            <span style="userSelect: none">{{
              $t(`menu.${page.meta.title}`)
            }}</span>
            <a-menu slot="overlay" @click="closeMenuClick">
              <a-menu-item key="close-that">
                {{ $t('multitab.closecurrent') }}
              </a-menu-item>
              <a-menu-item key="close-right">
                {{ $t('multitab.closecright') }}
              </a-menu-item>
              <a-menu-item key="close-left">
                {{ $t('multitab.closecleft') }}
              </a-menu-item>
              <a-menu-item key="close-all">
                {{ $t('multitab.closecall') }}
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </a-tab-pane>
      </a-tabs>
    </div>
  </div>
</template>
<script lang="ts">
import {Component, Prop, Vue, Watch} from 'vue-property-decorator';
@Component({
  components: {},
})
export default class MultiTab extends Vue {
  public fullPathList: Array<any> = [];
  public pages: Array<any> = [];
  public activeKey: string = '';
  public newTabIndex: number = 0;

  get tabBarStyle() {
    return {
      background: '#FFF',
      margin: 0,
      paddingLeft: '16px',
      paddingTop: '1px',
    };
  }

  public onEdit(targetKey: any, action: string) {
    if (action === 'remove') this.remove(targetKey);
  }
  remove(targetKey: string) {
    this.pages = this.pages.filter(page => page.fullPath !== targetKey);
    this.fullPathList = this.fullPathList.filter(path => path !== targetKey);
    if (!this.fullPathList.includes(this.activeKey)) {
      this.selectedLastPath();
    }
  }

  selectedLastPath() {
    this.activeKey = this.fullPathList[this.fullPathList.length - 1];
  }

  closeMenuClick({key, domEvent}: {key: string; domEvent: any}) {
    const vkey = domEvent.target.getAttribute('data-vkey');
    switch (key) {
      case 'close-right':
        this.closeRight(vkey);
        break;
      case 'close-left':
        this.closeLeft(vkey);
        break;
      case 'close-all':
        this.closeAll(vkey);
        break;
      default:
      case 'close-that':
        this.closeThat(vkey);
        break;
    }
  }

  closeThat(e: any) {
    this.remove(e);
  }
  closeLeft(e: any) {
    const currentIndex = this.fullPathList.indexOf(e);
    if (currentIndex > 0) {
      this.fullPathList.forEach((item, index) => {
        if (index < currentIndex) {
          this.remove(item);
        }
      });
    } else {
      this.$message.info(this.$t('multitab.leftHasNoTag'));
    }
  }
  closeRight(e: any) {
    const currentIndex = this.fullPathList.indexOf(e);
    if (currentIndex < this.fullPathList.length - 1) {
      this.fullPathList.forEach((item, index) => {
        if (index > currentIndex) {
          this.remove(item);
        }
      });
    } else {
      this.$message.info(this.$t('multitab.rightHasNoTag'));
    }
  }
  closeAll(e: any) {
    const currentIndex = this.fullPathList.indexOf(e);
    this.fullPathList.forEach((item, index) => {
      if (index !== currentIndex) {
        this.remove(item);
      }
    });
  }

  created() {
    this.pages.push(this.$route);
    this.fullPathList.push(this.$route.fullPath);
    this.selectedLastPath();
  }
  @Watch('$route')
  routerChnaged(newVal: any) {
    this.activeKey = newVal.fullPath;
    if (this.fullPathList.indexOf(newVal.fullPath) < 0) {
      this.fullPathList.push(newVal.fullPath);
      this.pages.push(newVal);
    }
  }

  handleChange(key: string) {
    this.$router.push({path: key});
  }
}
</script>
<style lang="less" scoped></style>
