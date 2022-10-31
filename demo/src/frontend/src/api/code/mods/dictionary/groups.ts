/**
 * @desc 分页查询码本分组
 */
import { defaultSuccess, defaultError, http } from '@/plugins/axios';
import type { AxiosResponse } from 'axios';
import type { Pagination, GlobalError } from '@/api/api';
export interface Params {
  /** 页码 */
  page?: number;
  /** 页面大小 */
  size?: number;
  /** 搜索关键词 */
  key?: string;
}

export default async function (
  params: Params,
  success: (data: Pagination<code.Group>) => void = defaultSuccess,
  fail: (error: GlobalError) => void = defaultError,
): Promise<void> {
  return http({
    method: 'get',
    url: `/groups`,

    params,
  })
    .then((data: AxiosResponse<Pagination<code.Group>, unknown>) => {
      success(data.data);
    })
    .catch((error: GlobalError) => fail(error));
}
