/**
 * @desc 用户详情
 */
import { defaultSuccess, defaultError, http } from '@/plugins/axios';
import type { AxiosResponse } from 'axios';
export default async function (
  /** 用户id */
  id: number,

  success: (data: acl.User) => void = defaultSuccess,
  fail: (error: string) => void = defaultError,
): Promise<void> {
  return http({
    method: 'get',
    url: `/user/${id}`,
  })
    .then((data: AxiosResponse<acl.User, unknown>) => {
      success(data.data);
    })
    .catch((error: string) => fail(error));
}