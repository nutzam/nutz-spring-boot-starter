/**
 * @description Auth Controller
 */
import check from './check';
import login from './login';

export class AuthApi {
  constructor(
    public check: (
      success?: ({
        data,
        ext,
        state,
        errors,
      }: {
        data: any;
        ext: ObjectMap;
        state: 'SUCCESS' | 'FAIL' | 'EXCEPTION';
        errors?: Array<string>;
      }) => any,
      fail?: (error: string) => any
    ) => void,

    public login: (
      login: defs.Login,

      success?: ({
        data,
        ext,
        state,
        errors,
      }: {
        data: defs.AuthUser;
        ext: ObjectMap;
        state: 'SUCCESS' | 'FAIL' | 'EXCEPTION';
        errors?: Array<string>;
      }) => any,
      fail?: (error: string) => any
    ) => void
  ) {}
}

export default {
  check,
  login,
} as AuthApi;
