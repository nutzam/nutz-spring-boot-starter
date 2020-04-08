/**
 * @desc captcha
 */

import {defaultSuccess, defaultError, http} from '@/plugins/axios';
export class CaptchaParams {
  constructor(public length?: number) {}
}

export default async function (
  params: CaptchaParams,
  success: ({
    data,
    ext,
    state,
    errors,
  }: {
    data: string;
    ext: ObjectMap;
    state: 'SUCCESS' | 'FAIL' | 'EXCEPTION';
    errors?: Array<string>;
  }) => any = defaultSuccess,
  fail: (error: string) => any = defaultError
) {
  try {
    const data: any = await http({
      method: 'get',
      url: `/captcha`,

      params,
    });
    return success(data);
  } catch (error) {
    return fail(error);
  }
}
