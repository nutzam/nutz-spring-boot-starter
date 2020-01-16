/**
 * @desc roles
 */

import {defaultSuccess, defaultError, http} from '@/plugins/axios';

export default async function(
  id: number,

  success: ({
    data,
    ext,
    state,
    errors,
  }: {
    data: Array<defs.RoleInfo>;
    ext: ObjectMap;
    state: 'SUCCESS' | 'FAIL' | 'EXCEPTION';
    errors?: Array<string>;
  }) => any = defaultSuccess,
  fail: (error: string) => any = defaultError
) {
  try {
    const data: any = await http({
      method: 'get',
      url: `/user/${id}/grant/role`,
    });
    return success(data);
  } catch (error) {
    return fail(error);
  }
}
