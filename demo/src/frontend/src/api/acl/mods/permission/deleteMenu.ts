/**
 * @desc 删除菜单
 */
import type { GlobalError } from '@/api/api';
import { defaultSuccess, defaultError, http } from '@/plugins/axios';
import type { AxiosResponse } from 'axios';
export default async function (
  /** 菜单key */
  key: string,

  success: (data: void) => void = defaultSuccess,
  fail: (error: GlobalError) => void = defaultError,
): Promise<void> {
  return http({
    method: 'delete',
    url: `/menu/${key}`,
  })
    .then((data: AxiosResponse<void, unknown>) => {
      success(data.data);
    })
    .catch((error: GlobalError) => fail(error));
}
