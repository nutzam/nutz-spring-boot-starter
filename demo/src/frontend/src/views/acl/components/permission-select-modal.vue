<template>
  <a-modal
    v-model:visible="visible"
    :title="$t('page.modal.permission.title')"
    :confirm-loading="confirmLoading"
    @ok="handleOk"
  >
    <a-tree
      v-model:checkedKeys="checkedKeys"
      default-expand-all
      checkable
      :height="233"
      :tree-data="treeData"
      :field-names="{ title: 'name', key: 'keyPath' }"
    ></a-tree>
  </a-modal>
</template>
<script lang="ts" setup>
import { computed } from 'vue';
import XEUtils from 'xe-utils';
const emit = defineEmits(['ok']);

const props = withDefaults(defineProps<{ permissions: Array<acl.PermissionInfo> }>(), {
  permissions: () => [],
});
const { permissions } = toRefs(props);
const visible = ref(false);
const confirmLoading = ref(false);
const checkedKeys = ref<string[]>([]);
watch(
  permissions,
  newVal => {
    console.log(newVal);
    checkedKeys.value = newVal.filter(item => item.selected).map(item => String(item.keyPath));
  },
  { deep: true, immediate: true },
);
const treeData = computed(() => {
  return XEUtils.toArrayTree(permissions.value, { key: 'key', parentKey: 'parentKey' });
});
const open = () => {
  visible.value = true;
  confirmLoading.value = false;
};

const close = () => {
  visible.value = false;
  confirmLoading.value = false;
};

const handleOk = () => {
  confirmLoading.value = true;
  emit('ok', checkedKeys.value);
};

defineExpose({ open, close });
</script>
<style lang="less" scoped></style>
