import {
  VuexModule,
  Module,
  Mutation,
  Action,
  getModule,
} from "vuex-module-decorators";
import store from "@/store";
import * as types from "@/store/mutation-types";
import storage from "store";

export interface UserState {
  /**
   * 用户名
   */
  name: string;
  /**
   * 姓名
   */
  fullName?: string;
  /**
   * 头像
   */
  avatarUrl?: string;
  /**
   * 邮箱
   */
  email?: string;
  /**
   * 电话
   */
  mobile?: string;
  /**
   * jwtToken
   */
  token: string;
  /**
   * 角色
   */
  roles: Array<string>;
  /**
   * 权限
   */
  permissions: Array<string>;
}

@Module({ dynamic: true, store, name: "user" })
class User extends VuexModule implements UserState {
  public name = "";
  public fullName = "";
  public avatarUrl = "";
  public email = "";
  public mobile = "";
  public token = "";
  public roles: Array<string> = [];
  public permissions: Array<string> = [];

  @Mutation
  public login(user: UserState) {
    storage.set(types.LOGIN_USER, user);
    Object.assign(this, user);
  }

  @Mutation
  public logout() {
    this.token = "";
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
