/**
 * @desc 当前用户
 */
import type { GlobalError } from '@/api/api';
import { defaultSuccess, defaultError, http } from '@/plugins/axios';
import type { AxiosResponse } from 'axios';
export default async function (
  success: (data: auth.LoginUser) => void = defaultSuccess,
  fail: (error: GlobalError) => void = defaultError,
): Promise<void> {
  return http({
    method: 'get',
    url: `/auth/current-user`,
  })
    .then((data: AxiosResponse<auth.LoginUser, unknown>) => {
      success(data.data);
    })
    .catch((error: GlobalError) => fail(error));
}
