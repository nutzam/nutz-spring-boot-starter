<template>
  <div class="login-container">
    <div class="layer">
      <div class="some-space">
        <div class="form">
          <h2>
            <img :src="logo" alt="" />
            {{ app.title }}
          </h2>
          <a-tabs centered default-active-key="1" @tab-click="loginTypeChange">
            <a-tab-pane key="1">
              <template #tab>
                <div class="tab-title">{{ $t('page.login.tab.user') }}</div>
              </template>
              <div class="item">
                <icon-font type="icon-user"></icon-font>
                <input
                  v-model="user"
                  autocomplete="off"
                  type="text"
                  class="input"
                  :placeholder="$t('page.login.user.placement.name')"
                />
              </div>
              <div class="item">
                <icon-font type="icon-password"></icon-font>
                <input
                  v-model="password"
                  autocomplete="off"
                  type="password"
                  class="input"
                  maxlength="20"
                  :placeholder="$t('page.login.user.placement.password')"
                  @keyup.enter="login"
                />
              </div>
              <button class="loginBtn" :disabled="isLoginAble" @click.stop="login">
                {{ $t('page.login.button.login') }}
              </button>
              <a-divider orientation="left" class="login-divider">
                {{ $t('page.login.button.social') }}
              </a-divider>
              <a-row>
                <a-col :span="8" :offset="8">
                  <a-row>
                    <a-col :span="8">
                      <a-avatar size="large" style="background-color: #87d06800">
                        <template #icon>
                          <icon-font type="icon-gitea" @click="gotoGiteaAuth" />
                        </template>
                      </a-avatar>
                    </a-col>
                  </a-row>
                </a-col>
                <a-col :span="8"></a-col>
              </a-row>
            </a-tab-pane>
            <a-tab-pane key="2" force-render>
              <template #tab>
                <div class="tab-title">{{ $t('page.login.tab.scan') }}</div>
              </template>
              <div id="loginQR" style="padding-left: 50px">
                <img width="300" alt="" />
              </div>
            </a-tab-pane>
          </a-tabs>
          <div class="tip" v-html="app.copyright"></div>
        </div>
      </div>
    </div>
  </div>
</template>
<script lang="ts" setup>
import { api } from '@/api';
import logo from '@/assets/logo.png';
import { IconFont } from '@/components/IconFont/IconFont';
import { useAppStore } from '@/store/app';
import { useUserStore } from '@/store/user';

const userStore = useUserStore();
const app = useAppStore();
const router = useRouter();
const user = ref('');
const password = ref('');

const isLoginAble = computed(() => {
  return !(user.value && password.value);
});
const loginTypeChange = (key: string) => {
  console.log(key);
  // if (key === '2') {
  //   api.authApi.oAuth.qrOption({ id: 'loginQR', state: 'SCAN', href: '' }, data => {
  //     new window.WwLogin(data);
  //   });
  // }
};

const login = () => {
  api.authApi.login.login({ name: user.value, password: password.value }, data => {
    userStore.userLogin(data);
    router.push({ path: '/' });
  });
};
const gotoGiteaAuth = () => {
  console.log('gotoGiteaAuth');
  // api.authApi.oAuth.loginUrl(data => {
  //   location.href = data;
  // });
};
</script>
<style lang="less" scoped>
@import './user.less';
</style>
