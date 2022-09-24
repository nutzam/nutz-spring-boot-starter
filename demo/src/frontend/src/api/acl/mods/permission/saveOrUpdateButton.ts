/**
 * @desc 增加/编辑操作按钮
 */
import type { GlobalError } from '@/api/api';
import { defaultSuccess, defaultError, http } from '@/plugins/axios';
import type { AxiosResponse } from 'axios';
export default async function (
  /** 请求体 */
  requestBody: acl.Button,

  success: (data: acl.Button) => void = defaultSuccess,
  fail: (error: GlobalError) => void = defaultError,
): Promise<void> {
  return http({
    method: 'put',
    url: `/button`,
    data: requestBody,
  })
    .then((data: AxiosResponse<acl.Button, unknown>) => {
      success(data.data);
    })
    .catch((error: GlobalError) => fail(error));
}
