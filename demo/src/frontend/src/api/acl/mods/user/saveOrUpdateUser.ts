/**
 * @desc 增加/编辑用户
 */
import { defaultSuccess, defaultError, http } from '@/plugins/axios';
import type { AxiosResponse } from 'axios';
export default async function (
  /** 请求体 */
  requestBody: acl.User,

  success: (data: acl.User) => void = defaultSuccess,
  fail: (error: string) => void = defaultError,
): Promise<void> {
  return http({
    method: 'put',
    url: `/user`,
    data: requestBody,
  })
    .then((data: AxiosResponse<acl.User, unknown>) => {
      success(data.data);
    })
    .catch((error: string) => fail(error));
}
