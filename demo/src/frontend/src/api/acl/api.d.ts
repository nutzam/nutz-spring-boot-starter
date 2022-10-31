declare namespace acl {
  /**
   * 增加权限
   */
  export interface Permission {
    /** createdTime */
    createdTime?: string;

    /** 权限描述 */
    description?: string;

    /** id */
    id?: number;

    /** 权限key,英文 */
    key?: string;

    /** 权限keyPath,用来做业务,(父级keyPath.key) */
    keyPath?: string;

    /** 权限名称,中文用来做标识 */
    name?: string;

    /** 父权限key */
    parentKey?: string;

    /** 权限类型 */
    type?: 'MENU' | 'BUTTON' | 'OTHER';

    /** typeInfo */
    typeInfo?: acl.Codebook;

    /** updatedTime */
    updatedTime?: string;
  }

  /**
   * 权限信息,包含是否选中标识
   */
  export interface PermissionInfo {
    /** createdTime */
    createdTime?: string;

    /** 权限描述 */
    description?: string;

    /** id */
    id?: number;

    /** 权限key,英文 */
    key?: string;

    /** 权限keyPath,用来做业务,(父级keyPath.key) */
    keyPath?: string;

    /** 权限名称,中文用来做标识 */
    name?: string;

    /** 父权限key */
    parentKey?: string;

    /** 权限是否选中标识 */
    selected: boolean;

    /** 权限类型 */
    type?: 'MENU' | 'BUTTON' | 'OTHER';

    /** typeInfo */
    typeInfo?: acl.Codebook;

    /** updatedTime */
    updatedTime?: string;
  }

  /**
   * 角色
   */
  export interface Role {
    /** createdTime */
    createdTime?: string;

    /** 角色描述 */
    description?: string;

    /** id */
    id?: number;

    /** 角色key,英文,用来做业务 */
    key: string;

    /** 角色名称,中文用来做标识 */
    name: string;

    /** updatedTime */
    updatedTime?: string;
  }

  /**
   * 角色信息,包含是否选中标识
   */
  export interface RoleInfo {
    /** createdTime */
    createdTime?: string;

    /** 角色描述 */
    description?: string;

    /** id */
    id?: number;

    /** 角色key,英文,用来做业务 */
    key: string;

    /** 角色名称,中文用来做标识 */
    name: string;

    /** 角色是否选中标识 */
    selected: boolean;

    /** updatedTime */
    updatedTime?: string;
  }

  /**
   * 树
   */
  export interface TreeString {
    /** 下级列表 */
    children?: Array<acl.TreeString>;

    /** 描述 */
    description?: string;

    /** ID */
    key: string;

    /** 名称 */
    name: string;

    /** originData */
    originData: acl.TreeableString;

    /** 父级 ID */
    parentKey?: string;
  }

  /**
   * 原始数据
   */
  export interface TreeableString {
    /** description */
    description?: string;

    /** key */
    key?: string;

    /** name */
    name?: string;

    /** parentKey */
    parentKey?: string;
  }

  /**
   * 用户
   */
  export interface User {
    /** createdTime */
    createdTime?: string;

    /** 邮箱 */
    email: string;

    /** 真实姓名 */
    fullName?: string;

    /** id */
    id?: number;

    /** 手机号 */
    mobile: string;

    /** 用户名 */
    name: string;

    /** 密码 */
    password: string;

    /** 性别 */
    sex: 'MALE' | 'FEMALE';

    /** sexInfo */
    sexInfo?: acl.Codebook;

    /** updatedTime */
    updatedTime?: string;
  }
}
