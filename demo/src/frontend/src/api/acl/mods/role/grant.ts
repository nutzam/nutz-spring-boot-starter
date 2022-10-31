/**
 * @desc 为指定角色授权
 */
import { defaultSuccess, defaultError, http } from '@/plugins/axios';
import type { AxiosResponse } from 'axios';
import type { GlobalError } from '@/api/api';

export default async function (
  key: string,
  /** 请求体 */
  requestBody: Array<string>,

  success: (data: void) => void = defaultSuccess,
  fail: (error: GlobalError) => void = defaultError,
): Promise<void> {
  return http({
    method: 'post',
    url: `/role/${key}/permissions`,
    data: requestBody,
  })
    .then((data: AxiosResponse<void, unknown>) => {
      success(data.data);
    })
    .catch((error: GlobalError) => fail(error));
}
