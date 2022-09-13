/**
 * @description 用户
 *
 */
import saveOrUpdateUser from './saveOrUpdateUser';
import sexes from './sexes';
import userDetail from './userDetail';
import deleteUser from './deleteUser';
import resetPassword from './resetPassword';
import users from './users';
import type { Pagination, Codebook } from '@/api/api';

export interface UsersParams {
  /** 页码 */
  page?: number;
  /** 页面大小 */
  size?: number;
  /** 性别 */
  sex?: 'MALE' | 'FEMALE';
  /** 搜索关键词 */
  key?: string;
}

export interface UserApi {
  /** 增加/编辑用户 */
  saveOrUpdateUser: (
    /** 请求体 */
    requestBody: acl.User,

    success?: (data: acl.User) => void,
    fail?: (error: string) => void,
  ) => void;

  /** 用户性别 */
  sexes: (
    success?: (data: Array<Codebook>) => void,
    fail?: (error: string) => void,
  ) => void;

  /** 用户详情 */
  userDetail: (
    /** 用户id */
    id: number,

    success?: (data: acl.User) => void,
    fail?: (error: string) => void,
  ) => void;

  /** 删除用户 */
  deleteUser: (
    /** 用户id */
    id: number,

    success?: (data: void) => void,
    fail?: (error: string) => void,
  ) => void;

  /** 重置密码 */
  resetPassword: (
    /** 用户名 */
    name: string,

    success?: (data: string) => void,
    fail?: (error: string) => void,
  ) => void;

  /** 分页查询用户 */
  users: (
    params: UsersParams,
    success?: (data: Pagination<acl.User>) => void,
    fail?: (error: string) => void,
  ) => void;
}
export default {
  saveOrUpdateUser,
  sexes,
  userDetail,
  deleteUser,
  resetPassword,
  users,
} as UserApi;
