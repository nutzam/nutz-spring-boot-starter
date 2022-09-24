/**
 * @desc 分页查询角色
 */
import type { GlobalError } from '@/api/api';
import { defaultSuccess, defaultError, http } from '@/plugins/axios';
import type { AxiosResponse } from 'axios';
import type { Pagination } from '@/api/api';
import type { RolesParams } from './index';

export default async function (
  params: RolesParams,
  success: (data: Pagination<acl.Role>) => void = defaultSuccess,
  fail: (error: GlobalError) => void = defaultError,
): Promise<void> {
  return http({
    method: 'get',
    url: `/roles`,

    params,
  })
    .then((data: AxiosResponse<Pagination<acl.Role>, unknown>) => {
      success(data.data);
    })
    .catch((error: GlobalError) => fail(error));
}
