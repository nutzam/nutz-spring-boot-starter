/**
 * @desc 角色详情
 */
import type { GlobalError } from '@/api/api';
import { defaultSuccess, defaultError, http } from '@/plugins/axios';
import type { AxiosResponse } from 'axios';
export default async function (
  /** 角色id */
  id: number,

  success: (data: acl.Role) => void = defaultSuccess,
  fail: (error: GlobalError) => void = defaultError,
): Promise<void> {
  return http({
    method: 'get',
    url: `/role/${id}`,
  })
    .then((data: AxiosResponse<acl.Role, unknown>) => {
      success(data.data);
    })
    .catch((error: GlobalError) => fail(error));
}
