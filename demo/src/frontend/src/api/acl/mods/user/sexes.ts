/**
 * @desc 用户性别
 */
import { defaultSuccess, defaultError, http } from '@/plugins/axios';
import type { AxiosResponse } from 'axios';
import type { Codebook, GlobalError } from '@/api/api';

export default async function (
  success: (data: Array<Codebook>) => void = defaultSuccess,
  fail: (error: GlobalError) => void = defaultError,
): Promise<void> {
  return http({
    method: 'get',
    url: `/user/sexes`,
  })
    .then((data: AxiosResponse<Array<Codebook>, unknown>) => {
      success(data.data);
    })
    .catch((error: GlobalError) => fail(error));
}
