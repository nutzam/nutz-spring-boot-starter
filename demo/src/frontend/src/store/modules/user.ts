import Vue from 'vue';

import {
  VuexModule,
  Module,
  Mutation,
  Action,
  getModule,
} from 'vuex-module-decorators';
import store from '@/store';
import * as types from '@/store/mutation-types';

export interface IUserState {
  token: string | null;
  name: string;
  welcome: string;
  avatar: string;
  roles: Array<string>;
  permissions: Array<string>;
}

@Module({dynamic: true, store, name: 'user'})
class User extends VuexModule implements IUserState {
  public token: string | null = null;
  public name: string = 'Admin';
  public welcome: string = '';
  public avatar: string = '';
  public roles: Array<string> = [];
  public permissions: Array<string> = [];

  @Mutation
  public login(user: IUserState) {
    Vue.ls.set(types.LOGIN_USER, user);
    Object.assign(this, user);
  }

  @Mutation
  public logout() {
    Vue.ls.set(types.LOGIN_USER, {});
    this.token = null;
  }
  @Action
  public hasRole(role: string) {
    if (!role || role == null) return true;
    return this.roles.filter((r) => r === role).length > 0;
  }
  @Action
  public hasPermission(permission: string) {
    if (!permission || permission == null) return true;
    return this.permissions.filter((p) => p === permission).length > 0;
  }
}

export const UserModule = getModule(User);
