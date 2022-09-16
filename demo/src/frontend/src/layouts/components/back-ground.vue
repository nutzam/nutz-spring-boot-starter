<template>
  <li :style="style"></li>
</template>
<script lang="ts" setup>
const props = defineProps({
  image: {
    type: String,
    required: true,
  },
  index: {
    type: Number,
    required: true,
  },
});
const { image, index } = toRefs(props);
const style = computed(() => {
  return {
    backgroundImage: `url(${image.value})`,
    animationDelay: `${6 * index.value}s`,
  };
});
</script>
<style lang="less" scoped></style>
