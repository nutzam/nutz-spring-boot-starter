/**
 * @desc 查询全部菜单
 */
import type { GlobalError } from '@/api/api';
import { defaultSuccess, defaultError, http } from '@/plugins/axios';
import type { AxiosResponse } from 'axios';
import type { MenusParams } from './index';

export default async function (
  params: MenusParams,
  success: (data: Array<acl.Menu>) => void = defaultSuccess,
  fail: (error: GlobalError) => void = defaultError,
): Promise<void> {
  return http({
    method: 'get',
    url: `/menus`,

    params,
  })
    .then((data: AxiosResponse<Array<acl.Menu>, unknown>) => {
      success(data.data);
    })
    .catch((error: GlobalError) => fail(error));
}
