/**
 * @description 角色
 *
 */
import type { GlobalError } from '@/api/api';
import saveOrUpdateRole from './saveOrUpdateRole';
import roleDetail from './roleDetail';
import deleteRole from './deleteRole';
import permissionInfos from './permissionInfos';
import permissions from './permissions';
import grant from './grant';
import roles from './roles';
import type { Pagination } from '@/api/api';

export interface RolesParams {
  /** 页码 */
  page?: number;
  /** 页面大小 */
  size?: number;
  /** 搜索关键词 */
  key?: string;
}

export interface RoleApi {
  /** 增加/编辑角色 */
  saveOrUpdateRole: (
    /** 请求体 */
    requestBody: acl.Role,

    success?: (data: acl.Role) => void,
    fail?: (error: GlobalError) => void,
  ) => void;

  /** 角色详情 */
  roleDetail: (
    /** 角色id */
    id: number,

    success?: (data: acl.Role) => void,
    fail?: (error: GlobalError) => void,
  ) => void;

  /** 删除角色 */
  deleteRole: (
    /** 角色名称 */
    key: string,

    success?: (data: void) => void,
    fail?: (error: GlobalError) => void,
  ) => void;

  /** 查询用于授权的权限信息 */
  permissionInfos: (
    /** 角色key */
    key: string,

    success?: (data: Array<acl.PermissionInfo>) => void,
    fail?: (error: GlobalError) => void,
  ) => void;

  /** 查询角色权限 */
  permissions: (
    /** 角色key */
    key: string,

    success?: (data: Array<acl.MenuInfo>) => void,
    fail?: (error: GlobalError) => void,
  ) => void;

  /** 为指定角色授权 */
  grant: (
    key: string,
    /** 请求体 */
    requestBody: Array<string>,

    success?: (data: void) => void,
    fail?: (error: GlobalError) => void,
  ) => void;

  /** 分页查询角色 */
  roles: (
    params: RolesParams,
    success?: (data: Pagination<acl.Role>) => void,
    fail?: (error: GlobalError) => void,
  ) => void;
}
export default {
  saveOrUpdateRole,
  roleDetail,
  deleteRole,
  permissionInfos,
  permissions,
  grant,
  roles,
} as RoleApi;
