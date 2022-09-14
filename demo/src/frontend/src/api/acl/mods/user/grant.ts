/**
 * @desc 为指定用户授权
 */
import { defaultSuccess, defaultError, http } from '@/plugins/axios';
import type { AxiosResponse } from 'axios';
export default async function (
  name: string,
  /** 请求体 */
  requestBody: Array<string>,

  success: (data: void) => void = defaultSuccess,
  fail: (error: string) => void = defaultError,
): Promise<void> {
  return http({
    method: 'post',
    url: `/user/${name}/permissions`,
    data: requestBody,
  })
    .then((data: AxiosResponse<void, unknown>) => {
      success(data.data);
    })
    .catch((error: string) => fail(error));
}
