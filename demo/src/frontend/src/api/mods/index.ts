import action, {ActionApi} from './action';

import auth, {AuthApi} from './auth';

import captcha, {CaptchaApi} from './captcha';

import codeBook, {CodeBookApi} from './codeBook';

import group, {GroupApi} from './group';

import module, {ModuleApi} from './module';

import role, {RoleApi} from './role';

import test, {TestApi} from './test';

import user, {UserApi} from './user';

export class Api {
  constructor(
    public action: ActionApi,
    public auth: AuthApi,
    public captcha: CaptchaApi,
    public codeBook: CodeBookApi,
    public group: GroupApi,
    public module: ModuleApi,
    public role: RoleApi,
    public test: TestApi,
    public user: UserApi
  ) {}
}

export default {
  action,
  auth,
  captcha,
  codeBook,
  group,
  module,
  role,
  test,
  user,
} as Api;
