/**
 * @desc 重置密码
 */
import { defaultSuccess, defaultError, http } from '@/plugins/axios';
import type { AxiosResponse } from 'axios';
export default async function (
  /** 用户名 */
  name: string,

  success: (data: string) => void = defaultSuccess,
  fail: (error: string) => void = defaultError,
): Promise<void> {
  return http({
    method: 'patch',
    url: `/user/${name}/password`,
  })
    .then((data: AxiosResponse<string, unknown>) => {
      success(data.data);
    })
    .catch((error: string) => fail(error));
}
