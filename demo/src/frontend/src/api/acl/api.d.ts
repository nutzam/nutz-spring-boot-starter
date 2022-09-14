declare namespace acl {
  /**
   * 操作按钮
   */
  export class Button {
    /** createdTime */
    createdTime?: string;

    /** 资源描述 */
    description?: string;

    /** id */
    id?: number;

    /** 是否内置,内置的没有资源归属 */
    installed: boolean;

    /** 操作key,英文,用来做业务 */
    key: string;

    /** 归属资源key,如果内置为空 */
    menuKey: string;

    /** 操作名称,中文用来做标识 */
    name: string;

    /** updatedTime */
    updatedTime?: string;
  }

  /**
   * 菜单
   */
  export class Menu {
    /** createdTime */
    createdTime?: string;

    /** 资源描述 */
    description?: string;

    /** id */
    id?: number;

    /** 资源key,英文,用来做业务 */
    key: string;

    /** 资源名称,中文用来做标识 */
    name: string;

    /** 上级菜单key */
    parentKey?: string;

    /** updatedTime */
    updatedTime?: string;
  }

  /**
   * 菜单信息
   */
  export class MenuInfo {
    /** buttons */
    buttons?: Array<acl.Button>;

    /** createdTime */
    createdTime?: string;

    /** 资源描述 */
    description?: string;

    /** id */
    id?: number;

    /** 资源key,英文,用来做业务 */
    key: string;

    /** 资源名称,中文用来做标识 */
    name: string;

    /** 上级菜单key */
    parentKey?: string;

    /** updatedTime */
    updatedTime?: string;
  }

  /**
   * 权限信息
   */
  export class PermissionInfo {
    /** 按钮描述 */
    description?: string;

    /** 是否内置,内置的没有菜单归属 */
    installed?: boolean;

    /** 按钮key,英文,用来做业务 */
    key: string;

    /** 菜单描述 */
    menuDescription?: string;

    /** 菜单key,英文,用来做业务 */
    menuKey: string;

    /** 菜单名称,中文用来做标识 */
    menuName: string;

    /** 按钮名称,中文用来做标识 */
    name: string;

    /** 父菜单key */
    parentKey?: string;

    /** 权限是否选中标识 */
    selected: boolean;
  }

  /**
   * 角色
   */
  export class Role {
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
  export class RoleInfo {
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
   * 用户
   */
  export class User {
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
