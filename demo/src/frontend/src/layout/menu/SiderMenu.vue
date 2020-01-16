<template>
  <div>
    <Logo v-if="mode !== 'horizontal'" :title="AppModule.title" />
    <a-menu
      :selected-keys="selectedKeys"
      :open-keys="openKeys || []"
      :mode="mode"
      :theme="AppModule.navTheme"
      @click="handleClick"
      @openChange="onOpenChange"
    >
      <template v-for="item in menuData">
        <a-menu-item
          v-if="!item.children"
          :key="item.path"
          @click="
            () => {
              if ($route.fullpath !== item.path)
                $router.push({path: item.path});
            }
          "
        >
          <a-icon v-if="item.meta.icon" :type="item.meta.icon" />
          <span>{{ item.meta.localeTitle }}</span>
        </a-menu-item>
        <sub-menu v-else :menu-info="item" :key="item.path" />
      </template>
    </a-menu>
  </div>
</template>

<script lang="ts">
import {
  Vue,
  Component,
  Prop,
  Watch,
  Mixins,
  Emit,
} from 'vue-property-decorator';
import {Getter} from 'vuex-class';
import {Mixin, DeviceMixin} from '@/utils/mixin';
import {check} from '@/utils/auth';
import SubMenu from './SubMenu.vue';
import Logo from '../tools/Logo.vue';

@Component({
  components: {
    SubMenu,
    Logo,
  },
})
export default class SiderMenu extends Mixins(Mixin, DeviceMixin) {
  @Prop({default: false}) public collapsed!: boolean;
  @Prop({default: 'inline'}) public mode!: string;
  private menuData: any = [];
  private selectedKeys: string[] = [];
  private openKeys: string[] = [];
  private openKeysCache: string[] = [];
  private selectedKeysMap: any = {};
  private openKeysMap: any = {};

  private created() {
    this.menuData = this.getMenuData(
      (this.$router as any)['options'].routes.find(
        (item: any) => item.path === '/index'
      ).children
    );
    this.selectedKeys = this.selectedKeysMap[this.$route.path];
    // 如果为topmenu 不打开任何菜单
    if (this.mode === 'horizontal') {
      this.openKeys = [];
    } else {
      this.openKeys = this.collapsed ? [] : this.openKeysMap[this.$route.path];
    }
  }

  get rootSubmenuKeys(): string[] {
    const keys: string[] = [];
    this.menuData.forEach((item: any) => keys.push(item.path));
    return keys;
  }

  public go(path: string) {
    if (this.$route.path !== path) this.$router.push({path: path});
  }

  @Emit('handleClick')
  private handleClick({
    item,
    key,
    keyPath,
  }: {
    item: any;
    key: any;
    keyPath: any;
  }) {
    return {item, key, keyPath};
  }

  private onOpenChange(openKeys: any) {
    // 在水平模式下时执行，并且不再执行后续
    if (this.mode === 'horizontal') {
      this.openKeys = openKeys || [];
      return;
    }
    // 非水平模式时
    const latestOpenKey: string = this.openKeys
      ? openKeys.find((key: any) => !this.openKeys.includes(key))
      : [];
    if (this.rootSubmenuKeys.indexOf(latestOpenKey) === -1) {
      this.openKeys = openKeys || [];
    } else {
      this.openKeys = latestOpenKey ? ['/', latestOpenKey] : [];
    }
  }

  private getMenuData(
    routes: any[] = [],
    parentKeys: any[] = [],
    selectedKey?: string
  ): any {
    const menuData: any = [];
    for (let item of routes) {
      if (item.meta && item.meta.authority && !check(item.meta.authority)) {
        continue;
      }
      if (item.name && !item.hideInMenu) {
        // 由于subMenu为函数式组件 没有this实例 所以取不到$t
        // 在这里直接把title处理成当前语言
        // localeTitle 国际化显示的路由菜单名称
        if (item.meta && item.meta.title) {
          item.meta.localeTitle = this.$t(`menu.${item.meta.title}`);
        }
        this.openKeysMap[item.path] = parentKeys;
        this.selectedKeysMap[item.path] = [selectedKey || item.path];
        const newItem = {...item};
        delete newItem.children;
        if (item.children && !item.hideChildrenInMenu) {
          newItem.children = this.getMenuData(item.children, [
            ...parentKeys,
            item.path,
          ]);
        } else {
          this.getMenuData(
            item.children,
            selectedKey ? parentKeys : [...parentKeys, item.path],
            selectedKey || item.path
          );
        }
        menuData.push(newItem);
      } else if (
        !item.hideInMenu &&
        !item.hideChildrenInMenu &&
        item.children
      ) {
        menuData.push(
          ...this.getMenuData(item.children, [...parentKeys, item.path])
        );
      }
    }
    return menuData.filter((item: any) => item.meta);
  }

  @Watch('$route.path')
  routePathChange(val: any) {
    this.selectedKeys = this.selectedKeysMap[val];
    this.openKeys = this.collapsed
      ? []
      : this.openKeys.concat(this.openKeysMap[val]);
  }
  @Watch('AppModule.language')
  languageChange(val: string) {
    this.menuData = this.getMenuData(
      (this.$router as any)['options'].routes.find(
        (item: any) => item.path === '/index'
      ).children
    );
  }
  @Watch('collapsed')
  collapsedChange(val: boolean) {
    if (val) {
      // 缓存上一次openKeys
      this.openKeysCache = this.openKeys.concat();
      this.openKeys = [];
    } else {
      this.openKeys = this.openKeysCache;
    }
  }
}
</script>
<style lang="less" scoped></style>
