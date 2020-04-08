/**
 * @desc login
 */

import {defaultSuccess, defaultError, http} from '@/plugins/axios';

export default async function (
  login: defs.Login,

  success: ({
    data,
    ext,
    state,
    errors,
  }: {
    data: defs.AuthUser;
    ext: ObjectMap;
    state: 'SUCCESS' | 'FAIL' | 'EXCEPTION';
    errors?: Array<string>;
  }) => any = defaultSuccess,
  fail: (error: string) => any = defaultError
) {
  try {
    const data: any = await http({
      method: 'post',
      url: `/auth/login`,
      data: login,
    });
    return success(data);
  } catch (error) {
    return fail(error);
  }
}
