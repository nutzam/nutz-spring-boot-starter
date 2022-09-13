declare namespace auth {
  /**
   * AuthUser
   */
  export class AuthUser {
    /** extInfo */
    extInfo: auth.NutMap;

    /** 密码 */
    password: string;

    /** 权限列表 */
    permissions: Array<string>;

    /** jwt refreshToken */
    refreshToken: string;

    /** 角色列表 */
    roles: Array<string>;

    /** jwt Token */
    token: string;

    /** 用户名 */
    userName: string;
  }

  /**
   * LoginDto
   */
  export class LoginDto {
    /** 用户名 */
    name: string;

    /** 密码 */
    password: string;

    /** 是否记住我 */
    rememberMe?: boolean;
  }
}
