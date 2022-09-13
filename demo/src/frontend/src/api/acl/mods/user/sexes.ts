/**
 * @desc 用户性别
 */
import { defaultSuccess, defaultError, http } from '@/plugins/axios';
import type { AxiosResponse } from 'axios';
import type { Codebook } from '@/api/api';

export default async function (
  success: (data: Array<Codebook>) => void = defaultSuccess,
  fail: (error: string) => void = defaultError,
): Promise<void> {
  return http({
    method: 'get',
    url: `/user/sexes`,
  })
    .then((data: AxiosResponse<Array<Codebook>, unknown>) => {
      success(data.data);
    })
    .catch((error: string) => fail(error));
}
