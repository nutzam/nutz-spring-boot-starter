/**
 * @desc 当前用户
 */
import { defaultSuccess, defaultError, http } from '@/plugins/axios';
import type { AxiosResponse } from 'axios';
export default async function (
  success: (data: auth.LoginUser) => void = defaultSuccess,
  fail: (error: string) => void = defaultError,
): Promise<void> {
  return http({
    method: 'get',
    url: `/auth/current-user`,
  })
    .then((data: AxiosResponse<auth.LoginUser, unknown>) => {
      success(data.data);
    })
    .catch((error: string) => fail(error));
}
