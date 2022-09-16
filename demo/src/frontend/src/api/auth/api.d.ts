declare namespace auth {
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

  /**
   * 已登录用户
   */
  export class LoginUser {
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

    /** 权限列表 */
    permissions: Array<string>;

    /** refreshToken */
    refreshToken: string;

    /** 角色列表 */
    roles: Array<string>;

    /** 性别 */
    sex: 'MALE' | 'FEMALE';

    /** sexInfo */
    sexInfo?: auth.Codebook;

    /** token */
    token: string;

    /** updatedTime */
    updatedTime?: string;
  }
}
