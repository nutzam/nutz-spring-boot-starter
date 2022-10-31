/**
 * @desc 增加权限
 */
import { defaultSuccess, defaultError, http } from '@/plugins/axios';
import type { AxiosResponse } from 'axios';
import type { GlobalError } from '@/api/api';

export default async function (
  /** 请求体 */
  requestBody: acl.Permission,

  success: (data: acl.Permission) => void = defaultSuccess,
  fail: (error: GlobalError) => void = defaultError,
): Promise<void> {
  return http({
    method: 'put',
    url: `/permission`,
    data: requestBody,
  })
    .then((data: AxiosResponse<acl.Permission, unknown>) => {
      success(data.data);
    })
    .catch((error: GlobalError) => fail(error));
}
