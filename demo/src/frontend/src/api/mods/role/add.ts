/**
 * @desc add
 */

import {defaultSuccess, defaultError, http} from '@/plugins/axios';

export default async function(
  role: defs.Role,

  success: ({
    data,
    ext,
    state,
    errors,
  }: {
    data: defs.Role;
    ext: ObjectMap;
    state: 'SUCCESS' | 'FAIL' | 'EXCEPTION';
    errors?: Array<string>;
  }) => any = defaultSuccess,
  fail: (error: string) => any = defaultError
) {
  try {
    const data: any = await http({
      method: 'post',
      url: `/role`,
      data: role,
    });
    return success(data);
  } catch (error) {
    return fail(error);
  }
}
