/* eslint-disable @typescript-eslint/no-explicit-any */
/**
 * @desc 根据id获取指定用户在指定模块下的授权情况
 */
import { defaultSuccess, defaultError, http } from '@/plugins/axios';

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
    data: Array<acl.ModuleInfo>;
    ext: ObjectMap;
    state: 'SUCCESS' | 'FAIL' | 'EXCEPTION';
    errors?: Array<string>;
  }) => void = defaultSuccess,
  fail: (error: string) => void = defaultError,
): Promise<void> {
  return http({
    method: 'get',
    url: `/user/${id}/grant/info`,

    params,
  })
    .then(data => success(data as any))
    .catch(error => fail(error));
}
