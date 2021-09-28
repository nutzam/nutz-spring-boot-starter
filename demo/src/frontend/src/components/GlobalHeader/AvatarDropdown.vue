<template>
  <a-dropdown
    v-if="UserModule.fullName || UserModule.name"
    placement="bottomRight"
  >
    <span class="ant-pro-account-avatar">
      <a-avatar
        size="small"
        :src="
          UserModule.avatarUrl ||
          'https://gw.alipayobjects.com/zos/antfincdn/XAosXuNZyF/BiazfanxmamNRoxxVxka.png'
        "
        class="antd-pro-global-header-index-avatar"
      />
      <span>{{ UserModule.fullName || UserModule.name }}</span>
    </span>
    <template #overlay>
      <a-menu class="ant-pro-drop-down menu" :selected-keys="[]">
        <a-menu-item key="center" @click="handleToCenter">
          <a-icon type="user" />
          {{ t("usercenter") }}
        </a-menu-item>
        <a-menu-item key="settings" @click="handleToSettings">
          <a-icon type="setting" />
          {{ t("usercenter.setting") }}
        </a-menu-item>
        <a-menu-divider />
        <a-menu-item key="logout" @click="handleLogout">
          <a-icon type="logout" />
          {{ t("usercenter.logout") }}
        </a-menu-item>
      </a-menu>
    </template>
  </a-dropdown>
  <span v-else>
    <a-spin size="small" :style="{ marginLeft: 8, marginRight: 8 }" />
  </span>
</template>
<script lang="ts">
import { Component, Mixins } from "vue-property-decorator";
import { Modal } from "ant-design-vue";
import { i18nRender } from "@/locales";
import { I18nMixin, Mixin } from "@/utils/mixin";

@Component({
  components: {},
})
export default class AvatarDropdown extends Mixins(I18nMixin, Mixin) {
  handleToCenter(): void {
    this.$router.push({ path: "/account/center" });
  }

  handleToSettings(): void {
    this.$router.push({ path: "/account/settings" });
  }

  handleLogout(): void {
    Modal.confirm({
      title: i18nRender("layouts.usermenu.dialog.title"),
      content: i18nRender("layouts.usermenu.dialog.content"),
      onOk: async () => {
        await this.$store.dispatch("logout");
        this.$router.push({ path: "/" });
      },
    });
  }
}
</script>
<style lang="less" scoped>
.ant-pro-drop-down {
  /deep/ .action {
    margin-right: 8px;
  }
  /deep/ .ant-dropdown-menu-item {
    min-width: 160px;
  }
}
</style>
