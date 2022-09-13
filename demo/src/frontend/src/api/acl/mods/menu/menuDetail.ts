/**
 * @desc 菜单详情
 */
import { defaultSuccess, defaultError, http } from '@/plugins/axios';
import type { AxiosResponse } from 'axios';
export default async function (
  /** 菜单id */
  id: number,

  success: (data: acl.Menu) => void = defaultSuccess,
  fail: (error: string) => void = defaultError,
): Promise<void> {
  return http({
    method: 'get',
    url: `/menu/${id}`,
  })
    .then((data: AxiosResponse<acl.Menu, unknown>) => {
      success(data.data);
    })
    .catch((error: string) => fail(error));
}
