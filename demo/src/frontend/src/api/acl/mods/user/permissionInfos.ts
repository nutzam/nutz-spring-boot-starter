/**
 * @desc 查询用于授权的权限信息
 */
import { defaultSuccess, defaultError, http } from '@/plugins/axios';
import type { AxiosResponse } from 'axios';
export default async function (
  /** 用户名 */
  name: string,

  success: (data: Array<acl.PermissionInfo>) => void = defaultSuccess,
  fail: (error: string) => void = defaultError,
): Promise<void> {
  return http({
    method: 'get',
    url: `/user/${name}/permission-infos`,
  })
    .then((data: AxiosResponse<Array<acl.PermissionInfo>, unknown>) => {
      success(data.data);
    })
    .catch((error: string) => fail(error));
}
