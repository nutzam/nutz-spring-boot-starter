/* eslint-disable @typescript-eslint/no-explicit-any */
/**
 * @desc 查询指定系统功能下的所有功能动作
 */
import { defaultSuccess, defaultError, http } from '@/plugins/axios';

export default async function(
  id: number,

  success: ({
    data,
    ext,
    state,
    errors,
  }: {
    data: Array<acl.Action>;
    ext: ObjectMap;
    state: 'SUCCESS' | 'FAIL' | 'EXCEPTION';
    errors?: Array<string>;
  }) => void = defaultSuccess,
  fail: (error: string) => void = defaultError,
): Promise<void> {
  return http({
    method: 'get',
    url: `/module/${id}/actions`,
  })
    .then(data => success(data as any))
    .catch(error => fail(error));
}
