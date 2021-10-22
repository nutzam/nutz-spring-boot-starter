/* eslint-disable @typescript-eslint/no-explicit-any */
/**
 * @desc 新增字典项
 */
import { defaultSuccess, defaultError, http } from '@/plugins/axios';

export default async function(
  codebook: dictionary.Codebook,

  success: ({
    data,
    ext,
    state,
    errors,
  }: {
    data: dictionary.Codebook;
    ext: ObjectMap;
    state: 'SUCCESS' | 'FAIL' | 'EXCEPTION';
    errors?: Array<string>;
  }) => void = defaultSuccess,
  fail: (error: string) => void = defaultError,
): Promise<void> {
  return http({
    method: 'post',
    url: `/code`,
    data: codebook,
  })
    .then(data => success(data as any))
    .catch(error => fail(error));
}
