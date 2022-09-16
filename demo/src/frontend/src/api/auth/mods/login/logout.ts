/**
 * @desc 登出
 */
import { defaultSuccess, defaultError, http } from '@/plugins/axios';
import type { AxiosResponse } from 'axios';
export default async function (
  success: (data: void) => void = defaultSuccess,
  fail: (error: string) => void = defaultError,
): Promise<void> {
  return http({
    method: 'post',
    url: `/auth/logout`,
  })
    .then((data: AxiosResponse<void, unknown>) => {
      success(data.data);
    })
    .catch((error: string) => fail(error));
}
