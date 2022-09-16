<template>
  <div>
    <router-view></router-view>
    <back-ground-animation :images="images"></back-ground-animation>
  </div>
</template>
<script lang="ts" setup>
import { useUserStore } from '@/store/user';
import BackGroundAnimation from './components/back-ground-animation.vue';

import bg from '@/assets/login/bg.jpg';
import bg1 from '@/assets/login/bg-1.png';
import bg2 from '@/assets/login/bg-2.jpg';
import bg3 from '@/assets/login/bg-4.jpg';
import bg4 from '@/assets/login/bg-5.jpg';
import { api } from '@/api';
const images = computed(() => {
  return [bg, bg1, bg2, bg3, bg4];
});
const user = useUserStore();
const router = useRouter();
if (user.token) {
  api.authApi.login.currentUser(
    data => {
      user.userLogin(data); //更新一下token状态,自动续杯
      router.push({ path: '/' });
    },
    () => {
      user.logout();
      router.push({ path: '/user' });
    },
  );
}
</script>
<style lang="less" scoped></style>
