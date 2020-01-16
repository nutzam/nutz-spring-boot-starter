/**
 * @desc add
 */

import {defaultSuccess, defaultError, http} from '@/plugins/axios';

export default async function(
  user: defs.User,

  success: ({
    data,
    ext,
    state,
    errors,
  }: {
    data: defs.User;
    ext: ObjectMap;
    state: 'SUCCESS' | 'FAIL' | 'EXCEPTION';
    errors?: Array<string>;
  }) => any = defaultSuccess,
  fail: (error: string) => any = defaultError
) {
  try {
    const data: any = await http({
      method: 'post',
      url: `/user`,
      data: user,
    });
    return success(data);
  } catch (error) {
    return fail(error);
  }
}
