/**
 * @desc 查询用户权限
 */
import { defaultSuccess, defaultError, http } from '@/plugins/axios';
import type { AxiosResponse } from 'axios';
import type { GlobalError } from '@/api/api';

export default async function (
  /** 用户名 */
  name: string,

  success: (data: Array<acl.Permission>) => void = defaultSuccess,
  fail: (error: GlobalError) => void = defaultError,
): Promise<void> {
  return http({
    method: 'get',
    url: `/user/${name}/permissions`,
  })
    .then((data: AxiosResponse<Array<acl.Permission>, unknown>) => {
      success(data.data);
    })
    .catch((error: GlobalError) => fail(error));
}
