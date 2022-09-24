/**
 * @description 登录
 *
 */
import type { GlobalError } from '@/api/api';
import currentUser from './currentUser';
import login from './login';
import logout from './logout';
export interface LoginApi {
  /** 当前用户 */
  currentUser: (success?: (data: auth.LoginUser) => void, fail?: (error: GlobalError) => void) => void;

  /** 登录 */
  login: (
    /** 请求体 */
    requestBody: auth.LoginDto,

    success?: (data: auth.LoginUser) => void,
    fail?: (error: GlobalError) => void,
  ) => void;

  /** 登出 */
  logout: (success?: (data: void) => void, fail?: (error: GlobalError) => void) => void;
}
export default {
  currentUser,
  login,
  logout,
} as LoginApi;
