import { ref, onUnmounted } from 'vue';
export const useTimer = (
  callback = (times: number, dur: number) => {
    console.log(dur, times);
  },
  step = 1000,
) => {
  let timerVariableId: NodeJS.Timer | undefined;
  let times = 0;
  const isPaused = ref(false);
  const stop = () => {
    if (timerVariableId) {
      clearInterval(timerVariableId);
      timerVariableId = undefined;
      resume();
    }
  };
  const start = () => {
    stop();
    if (!timerVariableId) {
      times = 0;
      timerVariableId = setInterval(() => {
        if (!isPaused.value) {
          times++;
          callback(times, step * times);
        }
      }, step);
    }
  };
  const pause = () => {
    isPaused.value = true;
  };
  const resume = () => {
    isPaused.value = false;
  };
  onUnmounted(() => {
    if (timerVariableId) {
      clearInterval(timerVariableId);
    }
  });
  return {
    start,
    stop,
    pause,
    resume,
    isPaused,
  };
};
