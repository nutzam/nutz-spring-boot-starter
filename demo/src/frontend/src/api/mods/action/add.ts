/**
 * @desc add
 */

import {defaultSuccess, defaultError, http} from '@/plugins/axios';

export default async function(
  action: defs.Action,

  success: ({
    data,
    ext,
    state,
    errors,
  }: {
    data: defs.Action;
    ext: ObjectMap;
    state: 'SUCCESS' | 'FAIL' | 'EXCEPTION';
    errors?: Array<string>;
  }) => any = defaultSuccess,
  fail: (error: string) => any = defaultError
) {
  try {
    const data: any = await http({
      method: 'post',
      url: `/action`,
      data: action,
    });
    return success(data);
  } catch (error) {
    return fail(error);
  }
}
