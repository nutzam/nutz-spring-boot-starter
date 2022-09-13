/**
 * @desc 分页查询码本数据
 */
import { defaultSuccess, defaultError, http } from '@/plugins/axios';
import type { AxiosResponse } from 'axios';
import type { Pagination } from '@/api/api';
import type { DictionarysParams } from './index';

export default async function (
  params: DictionarysParams,
  success: (data: Pagination<code.Dictionary>) => void = defaultSuccess,
  fail: (error: string) => void = defaultError,
): Promise<void> {
  return http({
    method: 'get',
    url: `/dictionarys`,

    params,
  })
    .then((data: AxiosResponse<Pagination<code.Dictionary>, unknown>) => {
      success(data.data);
    })
    .catch((error: string) => fail(error));
}
