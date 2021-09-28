import action, { ActionApi } from "./action";

import module, { ModuleApi } from "./module";

import role, { RoleApi } from "./role";

import user, { UserApi } from "./user";

export class AclApi {
  constructor(
    public action: ActionApi,
    public module: ModuleApi,
    public role: RoleApi,
    public user: UserApi
  ) {}
}

export default {
  action,
  module,
  role,
  user,
} as AclApi;
