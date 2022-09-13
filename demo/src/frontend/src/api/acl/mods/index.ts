import permission, { type PermissionApi } from './permission';

import role, { type RoleApi } from './role';

import user, { type UserApi } from './user';

export interface AclApi {
  permission: PermissionApi;
  role: RoleApi;
  user: UserApi;
}

export default {
  permission,
  role,
  user,
} as AclApi;
