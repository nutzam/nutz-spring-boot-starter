/* eslint-disable @typescript-eslint/no-explicit-any */
/**
 * @description 登录认证模块
 */
import login, { LoginParams } from './login';

export class AuthApi {
  constructor(
    public login: (
      params: LoginParams,
      success?: ({
        data,
        ext,
        state,
        errors,
      }: {
        data: login.AuthUser;
        ext: ObjectMap;
        state: 'SUCCESS' | 'FAIL' | 'EXCEPTION';
        errors?: Array<string>;
      }) => any,
      fail?: (error: string) => any,
    ) => void,
  ) {}
}

export default {
  login,
} as AuthApi;
