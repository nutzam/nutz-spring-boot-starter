import { onMounted, onUnmounted } from 'vue';
// eslint-disable-next-line @typescript-eslint/no-empty-function
export const useScrollToBottom = (callback = () => {}) => {
  const handleScrolling = () => {
    if (window.innerHeight + window.scrollY >= document.body.scrollHeight) {
      callback();
    }
  };
  onMounted(() => {
    window.addEventListener('scroll', handleScrolling);
  });
  onUnmounted(() => {
    window.removeEventListener('scroll', handleScrolling);
  });
};
