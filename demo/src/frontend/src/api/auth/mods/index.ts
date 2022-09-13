import login, { type LoginApi } from './login';

export interface AuthApi {
  login: LoginApi;
}

export default {
  login,
} as AuthApi;
