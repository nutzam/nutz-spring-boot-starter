/**
 * @desc 分页查询用户
 */
import type { GlobalError } from '@/api/api';
import { defaultSuccess, defaultError, http } from '@/plugins/axios';
import type { AxiosResponse } from 'axios';
import type { Pagination } from '@/api/api';
import type { UsersParams } from './index';

export default async function (
  params: UsersParams,
  success: (data: Pagination<acl.User>) => void = defaultSuccess,
  fail: (error: GlobalError) => void = defaultError,
): Promise<void> {
  return http({
    method: 'get',
    url: `/users`,

    params,
  })
    .then((data: AxiosResponse<Pagination<acl.User>, unknown>) => {
      success(data.data);
    })
    .catch((error: GlobalError) => fail(error));
}
