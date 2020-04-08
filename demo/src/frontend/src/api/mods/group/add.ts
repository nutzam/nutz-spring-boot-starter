/**
 * @desc add
 */

import {defaultSuccess, defaultError, http} from '@/plugins/axios';

export default async function (
  group: defs.Group,

  success: ({
    data,
    ext,
    state,
    errors,
  }: {
    data: defs.Group;
    ext: ObjectMap;
    state: 'SUCCESS' | 'FAIL' | 'EXCEPTION';
    errors?: Array<string>;
  }) => any = defaultSuccess,
  fail: (error: string) => any = defaultError
) {
  try {
    const data: any = await http({
      method: 'post',
      url: `/group`,
      data: group,
    });
    return success(data);
  } catch (error) {
    return fail(error);
  }
}
