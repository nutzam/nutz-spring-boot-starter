import button, { type ButtonApi } from './button';

import menu, { type MenuApi } from './menu';

import role, { type RoleApi } from './role';

import user, { type UserApi } from './user';

export interface AclApi {
  button: ButtonApi;
  menu: MenuApi;
  role: RoleApi;
  user: UserApi;
}

export default {
  button,
  menu,
  role,
  user,
} as AclApi;
