/**
 * @desc 分页查询码本分组
 */
import type { GlobalError } from '@/api/api';
import { defaultSuccess, defaultError, http } from '@/plugins/axios';
import type { AxiosResponse } from 'axios';
import type { Pagination } from '@/api/api';
import type { GroupsParams } from './index';

export default async function (
  params: GroupsParams,
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
