/* eslint-disable @typescript-eslint/no-explicit-any */
/**
 * @desc 获取指定分组下的全部字典
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
    data: Array<dictionary.Codebook>;
    ext: ObjectMap;
    state: 'SUCCESS' | 'FAIL' | 'EXCEPTION';
    errors?: Array<string>;
  }) => void = defaultSuccess,
  fail: (error: string) => void = defaultError,
): Promise<void> {
  return http({
    method: 'get',
    url: `/group/${id}/codes`,
  })
    .then(data => success(data as any))
    .catch(error => fail(error));
}
