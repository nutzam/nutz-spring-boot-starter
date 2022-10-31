/**
 * @desc 权限列表
 */
import { defaultSuccess, defaultError, http } from '@/plugins/axios';
import type { AxiosResponse } from 'axios';
import type { GlobalError } from '@/api/api';
export interface Params {
  /** 强制拉取 */
  force?: boolean;
}

export default async function (
  params: Params,
  success: (data: Array<acl.Permission>) => void = defaultSuccess,
  fail: (error: GlobalError) => void = defaultError,
): Promise<void> {
  return http({
    method: 'get',
    url: `/permissions`,

    params,
  })
    .then((data: AxiosResponse<Array<acl.Permission>, unknown>) => {
      success(data.data);
    })
    .catch((error: GlobalError) => fail(error));
}
