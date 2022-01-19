/* eslint-disable @typescript-eslint/no-explicit-any */
/**
 * @desc 获取网页授权地址(H5端,公众号),H5端通过此接口获取到授权地址,然后直接location跳转
 */
import { defaultSuccess, defaultError, http } from '@/plugins/axios';

export class AuthUrlParams {
  constructor(
    public state: string,
    public scope?: 'snsapi_base' | 'snsapi_userinfo',
  ) {}
}

export default async function(
  params: AuthUrlParams,
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
    url: `/social/login/auth-url`,

    params,
  })
    .then(data => success(data as any))
    .catch(error => fail(error));
}
