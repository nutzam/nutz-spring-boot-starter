/**
 * @description 权限
 *
 */
import saveOrUpdateButton from './saveOrUpdateButton';
import saveOrUpdateMenu from './saveOrUpdateMenu';
import deleteMenu from './deleteMenu';
import buttons from './buttons';
import deleteButton from './deleteButton';
import menus from './menus';
export interface MenusParams {
  /** 搜索关键词 */
  key?: string;
}

export interface PermissionApi {
  /** 增加/编辑操作按钮 */
  saveOrUpdateButton: (
    /** 请求体 */
    requestBody: acl.Button,

    success?: (data: acl.Button) => void,
    fail?: (error: string) => void,
  ) => void;

  /** 增加/编辑菜单 */
  saveOrUpdateMenu: (
    /** 请求体 */
    requestBody: acl.Menu,

    success?: (data: acl.Menu) => void,
    fail?: (error: string) => void,
  ) => void;

  /** 删除菜单 */
  deleteMenu: (
    /** 菜单key */
    key: string,

    success?: (data: void) => void,
    fail?: (error: string) => void,
  ) => void;

  /** 菜单下的操作按钮列表 */
  buttons: (
    /** 菜单key */
    key: string,

    success?: (data: Array<acl.Button>) => void,
    fail?: (error: string) => void,
  ) => void;

  /** 删除操作按钮 */
  deleteButton: (
    /** 菜单key */
    key: string,
    /** 操作按钮buttonKey */
    buttonKey: string,

    success?: (data: void) => void,
    fail?: (error: string) => void,
  ) => void;

  /** 查询全部菜单 */
  menus: (
    params: MenusParams,
    success?: (data: Array<acl.Menu>) => void,
    fail?: (error: string) => void,
  ) => void;
}
export default {
  saveOrUpdateButton,
  saveOrUpdateMenu,
  deleteMenu,
  buttons,
  deleteButton,
  menus,
} as PermissionApi;
