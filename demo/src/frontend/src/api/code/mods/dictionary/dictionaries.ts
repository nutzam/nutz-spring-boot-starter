/**
 * @desc 分组下的数据字典列表
 */
import { defaultSuccess, defaultError, http } from '@/plugins/axios';
import type { AxiosResponse } from 'axios';
import type { GlobalError } from '@/api/api';

export default async function (
  /** 码本分组key */
  group: string,

  success: (data: Array<code.Dictionary>) => void = defaultSuccess,
  fail: (error: GlobalError) => void = defaultError,
): Promise<void> {
  return http({
    method: 'get',
    url: `/group/${group}/dictionaries`,
  })
    .then((data: AxiosResponse<Array<code.Dictionary>, unknown>) => {
      success(data.data);
    })
    .catch((error: GlobalError) => fail(error));
}
