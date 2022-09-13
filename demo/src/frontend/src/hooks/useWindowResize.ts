import { ref, onMounted, onUnmounted } from 'vue';
export function useWindowResize() {
  const width = ref(window.innerWidth);
  const height = ref(window.innerHeight);
  const handleResize = () => {
    width.value = window.innerWidth;
    height.value = window.innerHeight;
  };
  onMounted(() => {
    window.addEventListener('resize', handleResize);
  });
  onUnmounted(() => {
    window.removeEventListener('resize', handleResize);
  });
  return {
    width,
    height,
  };
}
