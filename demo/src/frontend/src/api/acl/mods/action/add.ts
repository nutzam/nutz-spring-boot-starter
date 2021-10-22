/* eslint-disable @typescript-eslint/no-explicit-any */
/**
 * @desc 新增功能动作
 */
import { defaultSuccess, defaultError, http } from '@/plugins/axios';

export default async function(
  action: acl.Action,

  success: ({
    data,
    ext,
    state,
    errors,
  }: {
    data: acl.Action;
    ext: ObjectMap;
    state: 'SUCCESS' | 'FAIL' | 'EXCEPTION';
    errors?: Array<string>;
  }) => void = defaultSuccess,
  fail: (error: string) => void = defaultError,
): Promise<void> {
  return http({
    method: 'post',
    url: `/action`,
    data: action,
  })
    .then(data => success(data as any))
    .catch(error => fail(error));
}
