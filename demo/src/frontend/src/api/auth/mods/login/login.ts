/**
 * @desc 登录
 */
import { defaultSuccess, defaultError, http } from '@/plugins/axios';
import type { AxiosResponse } from 'axios';
export default async function (
  /** 请求体 */
  requestBody: auth.LoginDto,

  success: (data: auth.AuthUser) => void = defaultSuccess,
  fail: (error: string) => void = defaultError,
): Promise<void> {
  return http({
    method: 'post',
    url: `/login`,
    data: requestBody,
  })
    .then((data: AxiosResponse<auth.AuthUser, unknown>) => {
      success(data.data);
    })
    .catch((error: string) => fail(error));
}
