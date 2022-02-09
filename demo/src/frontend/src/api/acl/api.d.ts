/* eslint-disable @typescript-eslint/no-unused-vars */
/* eslint-disable @typescript-eslint/no-explicit-any */

declare namespace acl {
  export class Action {
    /** createTime */
    createTime?: string;

    /** creater */
    creater?: string;

    /** id */
    id?: number;

    /** 是否内置标识 */
    installed: boolean;

    /** 动作key */
    key: string;

    /** 归属的模块id */
    moduleId: number;

    /** 动作名称 */
    name: string;

    /** updateTime */
    updateTime?: string;

    /** updater */
    updater?: string;
  }

  export class ActionInfo {
    /** 操作id */
    id: number;

    /** 操作key */
    key: string;

    /** 操作名称 */
    name: string;

    /** 是否选中标识 */
    selected?: boolean;
  }

  export class Module {
    /** createTime */
    createTime?: string;

    /** creater */
    creater?: string;

    /** 模块描述 */
    description?: string;

    /** id */
    id?: number;

    /** 模块key */
    key: string;

    /** 模块名称 */
    name: string;

    /** updateTime */
    updateTime?: string;

    /** updater */
    updater?: string;
  }

  export class ModuleInfo {
    /** 模块操作列表 */
    actions: Array<acl.ActionInfo>;

    /** 模块描述 */
    descr?: string;

    /** 模块Id */
    moduleId?: number;

    /** 模块key */
    moduleKey: string;

    /** 模块名称 */
    moduleName: string;

    /** 是否选中标识 */
    selected?: boolean;
  }

  export class PermissionInfo {
    /** 模块信息列表 */
    modules: Array<acl.ModuleInfo>;
  }

  export class Role {
    /** createTime */
    createTime?: string;

    /** creater */
    creater?: string;

    /** 角色描述 */
    description?: string;

    /** id */
    id?: number;

    /** 角色唯一键 */
    key: string;

    /** 角色名称 */
    name: string;

    /** updateTime */
    updateTime?: string;

    /** updater */
    updater?: string;
  }

  export class RoleInfo {
    /** createTime */
    createTime?: string;

    /** creater */
    creater?: string;

    /** 角色描述 */
    description?: string;

    /** id */
    id?: number;

    /** 角色唯一键 */
    key: string;

    /** 角色名称 */
    name: string;

    /** 是否选中标识 */
    selected?: boolean;

    /** updateTime */
    updateTime?: string;

    /** updater */
    updater?: string;
  }

  export class User {
    /** createTime */
    createTime?: string;

    /** creater */
    creater?: string;

    /** id */
    id?: number;

    /** 手机号 */
    mobile: string;

    /** 用户名 */
    name: string;

    /** 密码 */
    password: string;

    /** updateTime */
    updateTime?: string;

    /** updater */
    updater?: string;
  }

  export class Pagination<T0 = any> {
    /** dataList */
    dataList: Array<T0>;

    /** first */
    first?: boolean;

    /** last */
    last?: boolean;

    /** offset */
    offset?: number;

    /** pageCount */
    pageCount?: number;

    /** pageNumber */
    pageNumber?: number;

    /** pageSize */
    pageSize?: number;

    /** paras */
    paras?: ObjectMap<any, any>;

    /** recordCount */
    recordCount?: number;
  }

  export class Result<T0 = any> {
    /** 响应数据 */
    data: T0;

    /** 错误信息列表 */
    errors?: Array<string>;

    /** 响应扩展数据 */
    ext?: ObjectMap<any, any>;

    /** 响应状态 */
    state: "SUCCESS" | "FAIL" | "EXCEPTION";

    /** success */
    success?: boolean;
  }

  export class VXETableSaveDTO<T0 = any> {
    /** 新增记录数据 */
    insertRecords: Array<T0>;

    /** 删除记录数据 */
    removeRecords: Array<T0>;

    /** 更新记录数据 */
    updateRecords: Array<T0>;
  }
}
