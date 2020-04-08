/**
 * @desc edit
 */

import {defaultSuccess, defaultError, http} from '@/plugins/axios';

export default async function (
  module: defs.Module,

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
      method: 'put',
      url: `/module`,
      data: module,
    });
    return success(data);
  } catch (error) {
    return fail(error);
  }
}
