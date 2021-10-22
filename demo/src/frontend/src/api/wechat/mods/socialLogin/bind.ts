/* eslint-disable @typescript-eslint/no-explicit-any */
/**
 * @desc 绑定用户
 */
import { defaultSuccess, defaultError, http } from '@/plugins/axios';

export default async function(
  socialLoginBindDTO: wechat.TieSocialLoginUserDataObject,

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
    method: 'post',
    url: `/social/login/bind`,
    data: socialLoginBindDTO,
  })
    .then(data => success(data as any))
    .catch(error => fail(error));
}
