/**
 * @desc 增加/编辑码本数据
 */
import { defaultSuccess, defaultError, http } from '@/plugins/axios';
import type { AxiosResponse } from 'axios';
export default async function (
  /** 请求体 */
  requestBody: code.Dictionary,

  success: (data: code.Dictionary) => void = defaultSuccess,
  fail: (error: string) => void = defaultError,
): Promise<void> {
  return http({
    method: 'put',
    url: `/dictionary`,
    data: requestBody,
  })
    .then((data: AxiosResponse<code.Dictionary, unknown>) => {
      success(data.data);
    })
    .catch((error: string) => fail(error));
}
