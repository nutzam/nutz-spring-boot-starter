/**
 * @desc 查询用于授权的权限信息
 */
import { defaultSuccess, defaultError, http } from '@/plugins/axios';
import type { AxiosResponse } from 'axios';
import type { GlobalError } from '@/api/api';

export default async function (
  /** 角色key */
  key: string,

  success: (data: Array<acl.PermissionInfo>) => void = defaultSuccess,
  fail: (error: GlobalError) => void = defaultError,
): Promise<void> {
  return http({
    method: 'get',
    url: `/role/${key}/permission-infos`,
  })
    .then((data: AxiosResponse<Array<acl.PermissionInfo>, unknown>) => {
      success(data.data);
    })
    .catch((error: GlobalError) => fail(error));
}
