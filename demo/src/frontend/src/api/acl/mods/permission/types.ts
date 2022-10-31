/**
 * @desc 权限类型
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
    url: `/permission/types`,
  })
    .then((data: AxiosResponse<Array<Codebook>, unknown>) => {
      success(data.data);
    })
    .catch((error: GlobalError) => fail(error));
}
