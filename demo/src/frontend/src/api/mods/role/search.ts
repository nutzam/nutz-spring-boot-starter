/**
 * @desc search
 */

import {defaultSuccess, defaultError, http} from '@/plugins/axios';
export class SearchParams {
  constructor(
    public key?: string,
    public page?: number,
    public size?: number
  ) {}
}

export default async function(
  params: SearchParams,
  success: ({
    data,
    ext,
    state,
    errors,
  }: {
    data: defs.Pager<defs.Role>;
    ext: ObjectMap;
    state: 'SUCCESS' | 'FAIL' | 'EXCEPTION';
    errors?: Array<string>;
  }) => any = defaultSuccess,
  fail: (error: string) => any = defaultError
) {
  try {
    const data: any = await http({
      method: 'get',
      url: `/roles`,

      params,
    });
    return success(data);
  } catch (error) {
    return fail(error);
  }
}
