/**
 * @desc 分页查询操作按钮
 */
import { defaultSuccess, defaultError, http } from '@/plugins/axios';
import type { AxiosResponse } from 'axios';
import type { Pagination } from '@/api/api';
import type { ButtonsParams } from './index';

export default async function (
  params: ButtonsParams,
  success: (data: Pagination<acl.Button>) => void = defaultSuccess,
  fail: (error: string) => void = defaultError,
): Promise<void> {
  return http({
    method: 'get',
    url: `/buttons`,

    params,
  })
    .then((data: AxiosResponse<Pagination<acl.Button>, unknown>) => {
      success(data.data);
    })
    .catch((error: string) => fail(error));
}
