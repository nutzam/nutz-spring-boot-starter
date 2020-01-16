/**
 * @desc actions
 */

import {defaultSuccess, defaultError, http} from '@/plugins/axios';
export class ActionsParams {
  constructor(public mids?: Array<number>) {}
}

export default async function(
  id: number,

  params: ActionsParams,
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
      method: 'get',
      url: `/role/${id}/actions/info`,

      params,
    });
    return success(data);
  } catch (error) {
    return fail(error);
  }
}
