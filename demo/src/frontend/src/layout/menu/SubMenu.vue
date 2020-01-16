<template functional>
  <a-sub-menu :key="props.menuInfo.path">
    <span slot="title">
      <a-icon
        v-if="props.menuInfo.meta.icon"
        :type="props.menuInfo.meta.icon"
      />
      <span>{{ props.menuInfo.meta.localeTitle }}</span>
    </span>
    <template v-for="item in props.menuInfo.children">
      <a-menu-item
        v-if="!item.children"
        :key="item.path"
        @click="parent.go(item.path)"
      >
        <a-icon v-if="item.meta.icon" :type="item.meta.icon" />
        <span>{{ item.meta.localeTitle }}</span>
      </a-menu-item>
      <sub-menu v-else :key="item.path" :menu-info="item" />
    </template>
  </a-sub-menu>
</template>

<script lang="ts">
import {Vue, Component, Prop} from 'vue-property-decorator';

@Component
export default class SubMenu extends Vue {}
</script>
