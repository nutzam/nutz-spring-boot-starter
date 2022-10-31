/**
 * @desc 权限详情
 */
import { defaultSuccess, defaultError, http } from '@/plugins/axios';
import type { AxiosResponse } from 'axios';
import type { GlobalError } from '@/api/api';

export default async function (
  /** 权限id */
  id: number,

  success: (data: acl.Permission) => void = defaultSuccess,
  fail: (error: GlobalError) => void = defaultError,
): Promise<void> {
  return http({
    method: 'get',
    url: `/permission/${id}`,
  })
    .then((data: AxiosResponse<acl.Permission, unknown>) => {
      success(data.data);
    })
    .catch((error: GlobalError) => fail(error));
}
