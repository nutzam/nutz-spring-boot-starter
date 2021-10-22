import auth, { AuthApi } from './auth';

export class LoginApi {
  constructor(public auth: AuthApi) {}
}

export default {
  auth,
} as LoginApi;
