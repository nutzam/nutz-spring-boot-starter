/**
 * @desc 查询用户权限
 */
import type { GlobalError } from '@/api/api';
import { defaultSuccess, defaultError, http } from '@/plugins/axios';
import type { AxiosResponse } from 'axios';
export default async function (
  /** 用户名 */
  name: string,

  success: (data: Array<acl.RoleInfo>) => void = defaultSuccess,
  fail: (error: GlobalError) => void = defaultError,
): Promise<void> {
  return http({
    method: 'get',
    url: `/user/${name}/role-infos`,
  })
    .then((data: AxiosResponse<Array<acl.RoleInfo>, unknown>) => {
      success(data.data);
    })
    .catch((error: GlobalError) => fail(error));
}
