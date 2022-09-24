/**
 * @desc 增加/编辑码本数据
 */
import type { GlobalError } from '@/api/api';
import { defaultSuccess, defaultError, http } from '@/plugins/axios';
import type { AxiosResponse } from 'axios';
export default async function (
  group: string,
  /** 请求体 */
  requestBody: code.Dictionary,

  success: (data: code.Dictionary) => void = defaultSuccess,
  fail: (error: GlobalError) => void = defaultError,
): Promise<void> {
  return http({
    method: 'put',
    url: `/group/${group}/dictionary`,
    data: requestBody,
  })
    .then((data: AxiosResponse<code.Dictionary, unknown>) => {
      success(data.data);
    })
    .catch((error: GlobalError) => fail(error));
}
