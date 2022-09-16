<template>
  <ul class="slide-box">
    <back-ground v-for="(image, index) in images" :key="index" :image="image" :index="index"></back-ground>
  </ul>
</template>
<script lang="ts" setup>
import BackGround from './back-ground.vue';

const props = defineProps({
  images: {
    type: Array<string>,
    required: true,
  },
});
const { images } = toRefs(props);
</script>
<style lang="less" scoped>
.slide-box {
  position: fixed;
  width: 100%;
  height: 100%;
  background: rgba(245, 249, 250, 0.993);
  top: 0;
  left: 0;
  z-index: 0;
  li {
    width: 100%;
    height: 100%;
    position: absolute;
    top: 0px;
    left: 0px;
    color: transparent;
    background-size: cover;
    background-position: 50% 50%;
    background-repeat: none;
    opacity: 0;
    z-index: 0;
    backface-visibility: hidden;
    -webkit-backface-visibility: hidden;
    -webkit-animation: imgAnimation 30s linear infinite 0s;
    -moz-animation: imgAnimation 30s linear infinite 0s;
    -o-animation: imgAnimation 30s linear infinite 0s;
    -ms-animation: imgAnimation 30s linear infinite 0s;
    animation: imgAnimation 30s linear infinite 0s;
  }
}
@-webkit-keyframes imgAnimation {
  0% {
    opacity: 0;
    -webkit-animation-timing-function: ease-in;
  }
  8% {
    opacity: 1;
    -webkit-transform: scale(1.1);
    -webkit-animation-timing-function: ease-out;
  }
  17% {
    opacity: 1;
    -webkit-transform: scale(1.2);
  }
  25% {
    opacity: 0;
    -webkit-transform: scale(1.3);
  }
  100% {
    opacity: 0;
  }
}
@keyframes imgAnimation {
  0% {
    opacity: 0;
    animation-timing-function: ease-in;
  }
  8% {
    opacity: 1;
    transform: scale(1.1);
    animation-timing-function: ease-out;
  }
  17% {
    opacity: 1;
    transform: scale(1.2);
  }
  25% {
    opacity: 0;
    transform: scale(1.3);
  }
  100% {
    opacity: 0;
  }
}
</style>
