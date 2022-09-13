/**
 * @description 角色
 *
 */
import saveOrUpdateRole from './saveOrUpdateRole';
import roleDetail from './roleDetail';
import deleteRole from './deleteRole';
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
    fail?: (error: string) => void,
  ) => void;

  /** 角色详情 */
  roleDetail: (
    /** 角色id */
    id: number,

    success?: (data: acl.Role) => void,
    fail?: (error: string) => void,
  ) => void;

  /** 删除角色 */
  deleteRole: (
    /** 角色id */
    id: number,

    success?: (data: void) => void,
    fail?: (error: string) => void,
  ) => void;

  /** 分页查询角色 */
  roles: (
    params: RolesParams,
    success?: (data: Pagination<acl.Role>) => void,
    fail?: (error: string) => void,
  ) => void;
}
export default {
  saveOrUpdateRole,
  roleDetail,
  deleteRole,
  roles,
} as RoleApi;
