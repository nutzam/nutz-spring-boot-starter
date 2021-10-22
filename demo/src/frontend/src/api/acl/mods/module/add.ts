/* eslint-disable @typescript-eslint/no-explicit-any */
/**
 * @desc 创建系统功能
 */
import { defaultSuccess, defaultError, http } from '@/plugins/axios';

export default async function(
  module: acl.Module,

  success: ({
    data,
    ext,
    state,
    errors,
  }: {
    data: acl.Module;
    ext: ObjectMap;
    state: 'SUCCESS' | 'FAIL' | 'EXCEPTION';
    errors?: Array<string>;
  }) => void = defaultSuccess,
  fail: (error: string) => void = defaultError,
): Promise<void> {
  return http({
    method: 'post',
    url: `/module`,
    data: module,
  })
    .then(data => success(data as any))
    .catch(error => fail(error));
}
