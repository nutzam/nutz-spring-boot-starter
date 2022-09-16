import { defineStore } from 'pinia';
export const useUserStore = defineStore('user', {
  state: (): auth.LoginUser => ({
    mobile: '',
    email: '',
    name: '',
    password: '',
    permissions: [],
    roles: [],
    sex: 'FEMALE',
    token: '',
    refreshToken: '',
  }),
  actions: {
    userLogin(user: auth.LoginUser) {
      Object.assign(this, user);
    },
    logout() {
      this.token = '';
      this.refreshToken = '';
    },
  },
  persist: {
    enabled: true,
    strategies: [
      {
        key: 'user',
        storage: localStorage,
      },
    ],
  },
});
