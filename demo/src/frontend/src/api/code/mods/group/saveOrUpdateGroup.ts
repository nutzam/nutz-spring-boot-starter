/**
 * @desc 增加/编辑码本分组
 */
import { defaultSuccess, defaultError, http } from '@/plugins/axios';
import type { AxiosResponse } from 'axios';
export default async function (
  /** 请求体 */
  requestBody: code.Group,

  success: (data: code.Group) => void = defaultSuccess,
  fail: (error: string) => void = defaultError,
): Promise<void> {
  return http({
    method: 'put',
    url: `/group`,
    data: requestBody,
  })
    .then((data: AxiosResponse<code.Group, unknown>) => {
      success(data.data);
    })
    .catch((error: string) => fail(error));
}
