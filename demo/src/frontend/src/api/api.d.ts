type ObjectMap<Key extends string | number | symbol = any, Value = any> = {
  [key in Key]: Value;
};

declare namespace defs {
  export class Action {
    /** createTime */
    createTime?: string;

    /** id */
    id?: number;

    /** installed */
    installed: boolean;

    /** key */
    key: string;

    /** moduleId */
    moduleId?: number;

    /** name */
    name: string;

    /** updateTime */
    updateTime?: string;
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

  export class AuthUser {
    /** 扩展信息 */
    extInfo: ObjectMap<any, object>;

    /** 密码 */
    password: string;

    /** 权限列表 */
    permissions: Array<string>;

    /** 角色列表 */
    roles: Array<string>;

    /** jwt Token */
    token: string;

    /** User名 */
    userName: string;
  }

  export class CodeBook {
    /** createTime */
    createTime?: string;

    /** description */
    description?: string;

    /** disabled */
    disabled: boolean;

    /** groupId */
    groupId?: number;

    /** id */
    id?: number;

    /** index */
    index?: number;

    /** key */
    key: string;

    /** parentId */
    parentId?: number;

    /** updateTime */
    updateTime?: string;

    /** value */
    value: string;
  }

  export class Group {
    /** createTime */
    createTime?: string;

    /** description */
    description?: string;

    /** disabled */
    disabled: boolean;

    /** id */
    id?: number;

    /** key */
    key: string;

    /** name */
    name: string;

    /** updateTime */
    updateTime?: string;
  }

  export class Login {
    /** 验证码 */
    captcha: string;

    /** User名 */
    name: string;

    /** 密码 */
    password: string;

    /** 是否记住我 */
    rememberMe?: boolean;

    /** 验证码uuid */
    uuid: string;
  }

  export class Module {
    /** createTime */
    createTime?: string;

    /** descr */
    descr?: string;

    /** id */
    id?: number;

    /** key */
    key: string;

    /** name */
    name: string;

    /** updateTime */
    updateTime?: string;
  }

  export class ModuleInfo {
    /** 模块操作列表 */
    actions: Array<ActionInfo>;

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

  export class Pager<T0 = any> {
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
    paras?: ObjectMap<any, object>;

    /** recordCount */
    recordCount?: number;
  }

  export class PermissionInfo {
    /** 模块信息列表 */
    modules: Array<ModuleInfo>;
  }

  export class Result<T0 = any> {
    /** 响应数据 */
    data: T0;

    /** 错误信息列表 */
    errors?: Array<string>;

    /** 响应扩展数据 */
    ext?: ObjectMap<any, object>;

    /** 响应状态 */
    state: 'SUCCESS' | 'FAIL' | 'EXCEPTION';

    /** success */
    success?: boolean;
  }

  export class Role {
    /** createTime */
    createTime?: string;

    /** description */
    description?: string;

    /** id */
    id?: number;

    /** key */
    key: string;

    /** name */
    name: string;

    /** updateTime */
    updateTime?: string;
  }

  export class RoleInfo {
    /** createTime */
    createTime?: string;

    /** description */
    description?: string;

    /** id */
    id?: number;

    /** key */
    key: string;

    /** name */
    name: string;

    /** 是否选中标识 */
    selected?: boolean;

    /** updateTime */
    updateTime?: string;
  }

  export class TestIdCard {}

  export class User {
    /** createTime */
    createTime?: string;

    /** id */
    id?: number;

    /** mobile */
    mobile?: string;

    /** pwd */
    pwd: string;

    /** updateTime */
    updateTime?: string;

    /** userName */
    userName: string;
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
