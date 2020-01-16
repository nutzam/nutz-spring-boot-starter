/**
 * @desc grantInfo
 */

import {defaultSuccess, defaultError, http} from '@/plugins/axios';
export class GrantInfoParams {
  constructor(public mids?: Array<number>) {}
}

export default async function(
  id: number,

  params: GrantInfoParams,
  success: ({
    data,
    ext,
    state,
    errors,
  }: {
    data: Array<defs.ModuleInfo>;
    ext: ObjectMap;
    state: 'SUCCESS' | 'FAIL' | 'EXCEPTION';
    errors?: Array<string>;
  }) => any = defaultSuccess,
  fail: (error: string) => any = defaultError
) {
  try {
    const data: any = await http({
      method: 'get',
      url: `/role/${id}/grant/info`,

      params,
    });
    return success(data);
  } catch (error) {
    return fail(error);
  }
}
