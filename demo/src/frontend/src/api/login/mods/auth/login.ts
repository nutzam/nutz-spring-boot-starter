/* eslint-disable @typescript-eslint/no-explicit-any */
/**
 * @desc 用户登录
 */
import { defaultSuccess, defaultError, http } from '@/plugins/axios';

export class LoginParams {
  constructor(public password: string, public user: string) {}
}

export default async function(
  params: LoginParams,
  success: ({
    data,
    ext,
    state,
    errors,
  }: {
    data: login.AuthUser;
    ext: ObjectMap;
    state: 'SUCCESS' | 'FAIL' | 'EXCEPTION';
    errors?: Array<string>;
  }) => void = defaultSuccess,
  fail: (error: string) => void = defaultError,
): Promise<void> {
  return http({
    method: 'post',
    url: `/auth/login`,

    params,
  })
    .then(data => success(data as any))
    .catch(error => fail(error));
}
