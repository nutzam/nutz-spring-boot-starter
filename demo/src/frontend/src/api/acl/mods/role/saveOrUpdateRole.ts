/**
 * @desc 增加/编辑角色
 */
import { defaultSuccess, defaultError, http } from '@/plugins/axios';
import type { AxiosResponse } from 'axios';
export default async function (
  /** 请求体 */
  requestBody: acl.Role,

  success: (data: acl.Role) => void = defaultSuccess,
  fail: (error: string) => void = defaultError,
): Promise<void> {
  return http({
    method: 'put',
    url: `/role`,
    data: requestBody,
  })
    .then((data: AxiosResponse<acl.Role, unknown>) => {
      success(data.data);
    })
    .catch((error: string) => fail(error));
}