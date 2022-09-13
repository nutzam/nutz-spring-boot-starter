/**
 * @description 用户
 *
 */
import saveOrUpdateUser from './saveOrUpdateUser';
import userDetail from './userDetail';
import deleteUser from './deleteUser';
import users from './users';
import type { Pagination } from '@/api/api';

export interface UsersParams {
  /** 页码 */
  page?: number;
  /** 页面大小 */
  size?: number;
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

  /** 分页查询用户 */
  users: (params: UsersParams, success?: (data: Pagination<acl.User>) => void, fail?: (error: string) => void) => void;
}
export default {
  saveOrUpdateUser,
  userDetail,
  deleteUser,
  users,
} as UserApi;
