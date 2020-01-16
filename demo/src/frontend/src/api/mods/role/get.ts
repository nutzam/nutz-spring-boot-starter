/**
 * @desc get
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
    data: defs.Role;
    ext: ObjectMap;
    state: 'SUCCESS' | 'FAIL' | 'EXCEPTION';
    errors?: Array<string>;
  }) => any = defaultSuccess,
  fail: (error: string) => any = defaultError
) {
  try {
    const data: any = await http({
      method: 'get',
      url: `/role/${id}`,
    });
    return success(data);
  } catch (error) {
    return fail(error);
  }
}
