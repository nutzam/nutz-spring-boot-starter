/**
 * @desc 删除用户
 */
import { defaultSuccess, defaultError, http } from '@/plugins/axios';
import type { AxiosResponse } from 'axios';
import type { GlobalError } from '@/api/api';

export default async function (
  /** 用户id */
  id: number,

  success: (data: void) => void = defaultSuccess,
  fail: (error: GlobalError) => void = defaultError,
): Promise<void> {
  return http({
    method: 'delete',
    url: `/user/${id}`,
  })
    .then((data: AxiosResponse<void, unknown>) => {
      success(data.data);
    })
    .catch((error: GlobalError) => fail(error));
}
