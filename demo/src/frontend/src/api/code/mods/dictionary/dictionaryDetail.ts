/**
 * @desc 码本数据详情
 */
import { defaultSuccess, defaultError, http } from '@/plugins/axios';
import type { AxiosResponse } from 'axios';
export default async function (
  /** 码本数据id */
  id: number,

  success: (data: code.Dictionary) => void = defaultSuccess,
  fail: (error: string) => void = defaultError,
): Promise<void> {
  return http({
    method: 'get',
    url: `/dictionary/${id}`,
  })
    .then((data: AxiosResponse<code.Dictionary, unknown>) => {
      success(data.data);
    })
    .catch((error: string) => fail(error));
}
