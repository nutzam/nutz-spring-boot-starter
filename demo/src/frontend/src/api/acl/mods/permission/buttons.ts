/**
 * @desc 菜单下的操作按钮列表
 */
import type { GlobalError } from '@/api/api';
import { defaultSuccess, defaultError, http } from '@/plugins/axios';
import type { AxiosResponse } from 'axios';
export default async function (
  /** 菜单key */
  key: string,

  success: (data: Array<acl.Button>) => void = defaultSuccess,
  fail: (error: GlobalError) => void = defaultError,
): Promise<void> {
  return http({
    method: 'get',
    url: `/menu/${key}/buttons`,
  })
    .then((data: AxiosResponse<Array<acl.Button>, unknown>) => {
      success(data.data);
    })
    .catch((error: GlobalError) => fail(error));
}
