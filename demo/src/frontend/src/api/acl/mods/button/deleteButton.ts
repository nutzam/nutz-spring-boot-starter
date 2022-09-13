/**
 * @desc 删除操作按钮
 */
import { defaultSuccess, defaultError, http } from '@/plugins/axios';
import type { AxiosResponse } from 'axios';
export default async function (
  /** 操作按钮id */
  id: number,

  success: (data: void) => void = defaultSuccess,
  fail: (error: string) => void = defaultError,
): Promise<void> {
  return http({
    method: 'delete',
    url: `/button/${id}`,
  })
    .then((data: AxiosResponse<void, unknown>) => {
      success(data.data);
    })
    .catch((error: string) => fail(error));
}
