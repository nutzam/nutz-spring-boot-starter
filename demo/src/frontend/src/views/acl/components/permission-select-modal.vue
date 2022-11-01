<template>
  <a-modal
    v-model:visible="visible"
    :title="$t('page.modal.permission.title')"
    :confirm-loading="confirmLoading"
    :width="900"
    @ok="handleOk"
  >
    <vxe-table
      ref="xTree"
      :row-config="{ keyField: 'key' }"
      :column-config="{ resizable: true }"
      :tree-config="{ transform: true, rowField: 'key', parentField: 'parentKey', expandAll: true }"
      :data="permissions"
      :checkbox-config="checkboxConfig"
      @checkbox-change="selectChangeEvent"
    >
      <vxe-column show-overflow="tooltip" type="checkbox" title="key" width="280" tree-node></vxe-column>
      <vxe-column show-overflow="tooltip" field="keyPath" title="KeyPath"></vxe-column>
      <vxe-column show-overflow="tooltip" field="name" title="名称"></vxe-column>
      <vxe-column show-overflow="tooltip" field="description" title="描述"></vxe-column>
      <vxe-column show-overflow="tooltip" field="typeInfo.description" title="类型"></vxe-column>
    </vxe-table>
  </a-modal>
</template>
<script lang="ts" setup>
import type { VxeTableInstance } from 'vxe-table';
const emit = defineEmits(['ok']);

const props = withDefaults(defineProps<{ permissions: Array<acl.PermissionInfo> }>(), {
  permissions: () => [],
});
const { permissions } = toRefs(props);
const visible = ref(false);
const confirmLoading = ref(false);
const checkedKeys = ref<string[]>([]);
const checkRowKeys = ref<string[]>([]);
const xTree = ref<VxeTableInstance>();
watch(
  permissions,
  newVal => {
    checkRowKeys.value = newVal
      .filter(item => item.selected)
      .map(item => String(item.key))
      .filter(key => {
        return newVal.filter(p => p.parentKey === key && p.selected).length === 0;
      });
  },
  { deep: true, immediate: true },
);
const checkboxConfig = computed(() => {
  return { labelField: 'key', checkRowKeys: checkRowKeys.value };
});
const open = () => {
  visible.value = true;
  confirmLoading.value = false;
};

const close = () => {
  visible.value = false;
  permissions.value = [];
  confirmLoading.value = false;
};

const selectChangeEvent = ({ $table }: { $table: VxeTableInstance }) => {
  checkedKeys.value = [
    ...$table.getCheckboxRecords().map(item => item.keyPath),
    ...$table.getCheckboxIndeterminateRecords().map(item => item.keyPath),
  ];
};

const handleOk = () => {
  confirmLoading.value = true;
  permissions.value = [];
  emit('ok', checkedKeys.value);
};

defineExpose({ open, close });
nextTick(() => {
  xTree.value && xTree.value.reloadData(permissions.value);
});
</script>
<style lang="less" scoped></style>
