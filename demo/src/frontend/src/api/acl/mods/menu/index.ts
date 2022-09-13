/**
 * @description 菜单
 *
 */
import saveOrUpdateMenu from './saveOrUpdateMenu';
import menuDetail from './menuDetail';
import deleteMenu from './deleteMenu';
import menus from './menus';
import type { Pagination } from '@/api/api';

export interface MenusParams {
  /** 页码 */
  page?: number;
  /** 页面大小 */
  size?: number;
  /** 搜索关键词 */
  key?: string;
}

export interface MenuApi {
  /** 增加/编辑菜单 */
  saveOrUpdateMenu: (
    /** 请求体 */
    requestBody: acl.Menu,

    success?: (data: acl.Menu) => void,
    fail?: (error: string) => void,
  ) => void;

  /** 菜单详情 */
  menuDetail: (
    /** 菜单id */
    id: number,

    success?: (data: acl.Menu) => void,
    fail?: (error: string) => void,
  ) => void;

  /** 删除菜单 */
  deleteMenu: (
    /** 菜单id */
    id: number,

    success?: (data: void) => void,
    fail?: (error: string) => void,
  ) => void;

  /** 分页查询菜单 */
  menus: (
    params: MenusParams,
    success?: (data: Pagination<acl.Menu>) => void,
    fail?: (error: string) => void,
  ) => void;
}
export default {
  saveOrUpdateMenu,
  menuDetail,
  deleteMenu,
  menus,
} as MenuApi;
