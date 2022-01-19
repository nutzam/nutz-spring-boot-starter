/* eslint-disable @typescript-eslint/no-explicit-any */
/**
 * @desc 根据id删除系统功能
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
    data: void;
    ext: ObjectMap;
    state: 'SUCCESS' | 'FAIL' | 'EXCEPTION';
    errors?: Array<string>;
  }) => void = defaultSuccess,
  fail: (error: string) => void = defaultError,
): Promise<void> {
  return http({
    method: 'delete',
    url: `/module/${id}`,
  })
    .then(data => success(data as any))
    .catch(error => fail(error));
}
