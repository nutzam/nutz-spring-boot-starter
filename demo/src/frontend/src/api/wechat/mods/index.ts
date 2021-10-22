import socialLogin, { SocialLoginApi } from './socialLogin';

export class WechatApi {
  constructor(public socialLogin: SocialLoginApi) {}
}

export default {
  socialLogin,
} as WechatApi;
