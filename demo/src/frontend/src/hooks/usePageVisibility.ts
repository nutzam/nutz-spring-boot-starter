/* eslint-disable @typescript-eslint/no-explicit-any */
import { onMounted, onUnmounted } from 'vue';
export const usePageVisibility = (
  callback = (hidden: boolean) => {
    console.log(hidden);
  },
) => {
  let hidden: string, visibilityChange: string;
  if (typeof document.hidden !== 'undefined') {
    hidden = 'hidden';
    visibilityChange = 'visibilitychange';
  } else if (typeof (document as any)['msHidden'] !== 'undefined') {
    hidden = 'msHidden';
    visibilityChange = 'msvisibilitychange';
  } else if (typeof (document as any)['webkitHidden'] !== 'undefined') {
    hidden = 'webkitHidden';
    visibilityChange = 'webkitvisibilitychange';
  }
  const handleVisibilityChange = () => {
    callback((document as any)[hidden]);
  };
  onMounted(() => {
    document.addEventListener(visibilityChange, handleVisibilityChange, false);
  });
  onUnmounted(() => {
    document.removeEventListener(visibilityChange, handleVisibilityChange);
  });
};
