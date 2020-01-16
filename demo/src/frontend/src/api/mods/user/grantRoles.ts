/**
 * @desc grantRoles
 */

import {defaultSuccess, defaultError, http} from '@/plugins/axios';

export default async function(
  id: number,
  roles: Array<number>,

  success: ({
    data,
    ext,
    state,
    errors,
  }: {
    data: any;
    ext: ObjectMap;
    state: 'SUCCESS' | 'FAIL' | 'EXCEPTION';
    errors?: Array<string>;
  }) => any = defaultSuccess,
  fail: (error: string) => any = defaultError
) {
  try {
    const data: any = await http({
      method: 'post',
      url: `/user/${id}/grant/role`,
      data: roles,
    });
    return success(data);
  } catch (error) {
    return fail(error);
  }
}
