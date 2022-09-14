/**
 * @description 用户
 *
 */
import saveOrUpdateUser from './saveOrUpdateUser';
import sexes from './sexes';
import userDetail from './userDetail';
import deleteUser from './deleteUser';
import resetPassword from './resetPassword';
import permissionInfos from './permissionInfos';
import permissions from './permissions';
import grant from './grant';
import roleInfos from './roleInfos';
import grantRole from './grantRole';
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
  sexes: (success?: (data: Array<Codebook>) => void, fail?: (error: string) => void) => void;

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

  /** 查询用于授权的权限信息 */
  permissionInfos: (
    /** 用户名 */
    name: string,

    success?: (data: Array<acl.PermissionInfo>) => void,
    fail?: (error: string) => void,
  ) => void;

  /** 查询用户权限 */
  permissions: (
    /** 用户名 */
    name: string,

    success?: (data: Array<acl.MenuInfo>) => void,
    fail?: (error: string) => void,
  ) => void;

  /** 为指定用户授权 */
  grant: (
    name: string,
    /** 请求体 */
    requestBody: Array<string>,

    success?: (data: void) => void,
    fail?: (error: string) => void,
  ) => void;

  /** 查询用户权限 */
  roleInfos: (
    /** 用户名 */
    name: string,

    success?: (data: Array<acl.RoleInfo>) => void,
    fail?: (error: string) => void,
  ) => void;

  /** 为指定用户设置角色 */
  grantRole: (
    name: string,
    /** 请求体 */
    requestBody: Array<string>,

    success?: (data: void) => void,
    fail?: (error: string) => void,
  ) => void;

  /** 分页查询用户 */
  users: (params: UsersParams, success?: (data: Pagination<acl.User>) => void, fail?: (error: string) => void) => void;
}
export default {
  saveOrUpdateUser,
  sexes,
  userDetail,
  deleteUser,
  resetPassword,
  permissionInfos,
  permissions,
  grant,
  roleInfos,
  grantRole,
  users,
} as UserApi;
