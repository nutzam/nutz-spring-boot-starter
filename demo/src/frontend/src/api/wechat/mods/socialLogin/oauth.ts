/* eslint-disable @typescript-eslint/no-explicit-any */
/**
 * @desc 用code进行oauth登录,各端根据前置操作获取到code调用此接口,如果已经绑定,返回token,如果没有绑定则返回openid
 */
import { defaultSuccess, defaultError, http } from '@/plugins/axios';

export class OauthParams {
  constructor(public code: string) {}
}

export default async function(
  channel: 'MP' | 'MINIAPP' | 'WECHAT_SCAN' | 'WECHAT',

  params: OauthParams,
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
  }) => void = defaultSuccess,
  fail: (error: string) => void = defaultError,
): Promise<void> {
  return http({
    method: 'get',
    url: `/social/login/${channel}/oauth`,

    params,
  })
    .then(data => success(data as any))
    .catch(error => fail(error));
}
