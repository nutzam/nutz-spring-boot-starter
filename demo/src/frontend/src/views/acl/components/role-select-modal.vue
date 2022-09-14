<template>
  <a-modal
    v-model:visible="visible"
    :title="$t('page.modal.role.title')"
    :confirm-loading="confirmLoading"
    @ok="handleOk"
  >
    <a-transfer
      v-model:target-keys="targetKeys"
      v-model:selected-keys="selectedKeys"
      :data-source="roles"
      show-search
      :titles="titles"
      :render="itemRender"
      :list-style="{
        width: '215px',
        height: '300px',
      }"
    />
  </a-modal>
</template>
<script lang="ts" setup>
import { useI18n } from 'vue-i18n';
const { t } = useI18n();
const emit = defineEmits(['ok']);

const props = withDefaults(defineProps<{ roles: Array<acl.RoleInfo> }>(), {
  roles: () => [],
});

const { roles } = toRefs(props);
const visible = ref(false);
const confirmLoading = ref(false);
const targetKeys = ref<string[]>([]);
const selectedKeys = ref<string[]>([]);
const titles = [t('page.modal.role.source'), t('page.modal.role.target')];

watch(
  roles,
  (newVal: acl.RoleInfo[]) => {
    targetKeys.value = newVal.filter(item => item.selected).map(item => item.key);
  },
  { immediate: true, deep: true },
);
const open = () => {
  visible.value = true;
  confirmLoading.value = false;
};

const close = () => {
  visible.value = false;
  confirmLoading.value = false;
};

const itemRender = (item: acl.PermissionInfo) => {
  return `${item.name}`;
};

const handleOk = () => {
  confirmLoading.value = true;
  emit('ok', targetKeys.value);
};

defineExpose({ open, close });
</script>
<style lang="less" scoped></style>
