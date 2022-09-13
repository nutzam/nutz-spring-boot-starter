/**
 * @desc 查询全部菜单
 */
import { defaultSuccess, defaultError, http } from '@/plugins/axios';
import type { AxiosResponse } from 'axios';
import type { MenusParams } from './index';

export default async function (
  params: MenusParams,
  success: (data: Array<acl.Menu>) => void = defaultSuccess,
  fail: (error: string) => void = defaultError,
): Promise<void> {
  return http({
    method: 'get',
    url: `/menus`,

    params,
  })
    .then((data: AxiosResponse<Array<acl.Menu>, unknown>) => {
      success(data.data);
    })
    .catch((error: string) => fail(error));
}
