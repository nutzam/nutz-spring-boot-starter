/**
 * @desc 权限树
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
  success: (data: Array<acl.TreeString>) => void = defaultSuccess,
  fail: (error: GlobalError) => void = defaultError,
): Promise<void> {
  return http({
    method: 'get',
    url: `/permissions/tree`,

    params,
  })
    .then((data: AxiosResponse<Array<acl.TreeString>, unknown>) => {
      success(data.data);
    })
    .catch((error: GlobalError) => fail(error));
}
