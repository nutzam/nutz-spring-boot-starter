/**
 * @desc 增加/编辑菜单
 */
import { defaultSuccess, defaultError, http } from '@/plugins/axios';
import type { AxiosResponse } from 'axios';
export default async function (
  /** 请求体 */
  requestBody: acl.Menu,

  success: (data: acl.Menu) => void = defaultSuccess,
  fail: (error: string) => void = defaultError,
): Promise<void> {
  return http({
    method: 'put',
    url: `/menu`,
    data: requestBody,
  })
    .then((data: AxiosResponse<acl.Menu, unknown>) => {
      success(data.data);
    })
    .catch((error: string) => fail(error));
}
