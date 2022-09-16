/**
 * @desc 登录
 */
import { defaultSuccess, defaultError, http } from '@/plugins/axios';
import type { AxiosResponse } from 'axios';
export default async function (
  /** 请求体 */
  requestBody: auth.LoginDto,

  success: (data: auth.LoginUser) => void = defaultSuccess,
  fail: (error: string) => void = defaultError,
): Promise<void> {
  return http({
    method: 'post',
    url: `/auth/login`,
    data: requestBody,
  })
    .then((data: AxiosResponse<auth.LoginUser, unknown>) => {
      success(data.data);
    })
    .catch((error: string) => fail(error));
}
