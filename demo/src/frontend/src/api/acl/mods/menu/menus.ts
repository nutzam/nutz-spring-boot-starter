/**
 * @desc 分页查询菜单
 */
import { defaultSuccess, defaultError, http } from '@/plugins/axios';
import type { AxiosResponse } from 'axios';
import type { Pagination } from '@/api/api';
import type { MenusParams } from './index';

export default async function (
  params: MenusParams,
  success: (data: Pagination<acl.Menu>) => void = defaultSuccess,
  fail: (error: string) => void = defaultError,
): Promise<void> {
  return http({
    method: 'get',
    url: `/menus`,

    params,
  })
    .then((data: AxiosResponse<Pagination<acl.Menu>, unknown>) => {
      success(data.data);
    })
    .catch((error: string) => fail(error));
}
