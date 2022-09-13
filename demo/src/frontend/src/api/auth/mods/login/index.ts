/**
 * @description 登录
 *
 */
import login from './login';
export interface LoginApi {
  /** 登录 */
  login: (
    /** 请求体 */
    requestBody: auth.LoginDto,

    success?: (data: auth.AuthUser) => void,
    fail?: (error: string) => void,
  ) => void;
}
export default {
  login,
} as LoginApi;
