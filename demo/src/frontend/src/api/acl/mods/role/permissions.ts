/**
 * @desc 查询角色权限
 */
import type { GlobalError } from '@/api/api';
import { defaultSuccess, defaultError, http } from '@/plugins/axios';
import type { AxiosResponse } from 'axios';
export default async function (
  /** 角色key */
  key: string,

  success: (data: Array<acl.MenuInfo>) => void = defaultSuccess,
  fail: (error: GlobalError) => void = defaultError,
): Promise<void> {
  return http({
    method: 'get',
    url: `/role/${key}/permissions`,
  })
    .then((data: AxiosResponse<Array<acl.MenuInfo>, unknown>) => {
      success(data.data);
    })
    .catch((error: GlobalError) => fail(error));
}
