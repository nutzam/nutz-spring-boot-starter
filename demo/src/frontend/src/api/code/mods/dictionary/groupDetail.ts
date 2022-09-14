/**
 * @desc 码本分组详情
 */
import { defaultSuccess, defaultError, http } from '@/plugins/axios';
import type { AxiosResponse } from 'axios';
export default async function (
  /** 码本分组id */
  id: number,

  success: (data: code.Group) => void = defaultSuccess,
  fail: (error: string) => void = defaultError,
): Promise<void> {
  return http({
    method: 'get',
    url: `/group/${id}`,
  })
    .then((data: AxiosResponse<code.Group, unknown>) => {
      success(data.data);
    })
    .catch((error: string) => fail(error));
}
