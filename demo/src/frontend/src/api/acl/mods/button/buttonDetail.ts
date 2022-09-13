/**
 * @desc 操作按钮详情
 */
import { defaultSuccess, defaultError, http } from '@/plugins/axios';
import type { AxiosResponse } from 'axios';
export default async function (
  /** 操作按钮id */
  id: number,

  success: (data: acl.Button) => void = defaultSuccess,
  fail: (error: string) => void = defaultError,
): Promise<void> {
  return http({
    method: 'get',
    url: `/button/${id}`,
  })
    .then((data: AxiosResponse<acl.Button, unknown>) => {
      success(data.data);
    })
    .catch((error: string) => fail(error));
}
