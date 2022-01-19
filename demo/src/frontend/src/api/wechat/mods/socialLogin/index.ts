/* eslint-disable @typescript-eslint/no-explicit-any */
/**
 * @description Social Login Controller
 */
import authUrl, { AuthUrlParams } from './authUrl';
import bind from './bind';
import qrOption, { QrOptionParams } from './qrOption';
import oauth, { OauthParams } from './oauth';

export class SocialLoginApi {
  constructor(
    public authUrl: (
      params: AuthUrlParams,
      success?: ({
        data,
        ext,
        state,
        errors,
      }: {
        data: string;
        ext: ObjectMap;
        state: 'SUCCESS' | 'FAIL' | 'EXCEPTION';
        errors?: Array<string>;
      }) => any,
      fail?: (error: string) => any,
    ) => void,

    public bind: (
      socialLoginBindDTO: wechat.TieSocialLoginUserDataObject,

      success?: ({
        data,
        ext,
        state,
        errors,
      }: {
        data: string;
        ext: ObjectMap;
        state: 'SUCCESS' | 'FAIL' | 'EXCEPTION';
        errors?: Array<string>;
      }) => any,
      fail?: (error: string) => any,
    ) => void,

    public qrOption: (
      params: QrOptionParams,
      success?: ({
        data,
        ext,
        state,
        errors,
      }: {
        data: wechat.WxLogin;
        ext: ObjectMap;
        state: 'SUCCESS' | 'FAIL' | 'EXCEPTION';
        errors?: Array<string>;
      }) => any,
      fail?: (error: string) => any,
    ) => void,

    public oauth: (
      channel: 'MP' | 'MINIAPP' | 'WECHAT_SCAN' | 'WECHAT',

      params: OauthParams,
      success?: ({
        data,
        ext,
        state,
        errors,
      }: {
        data: string;
        ext: ObjectMap;
        state: 'SUCCESS' | 'FAIL' | 'EXCEPTION';
        errors?: Array<string>;
      }) => any,
      fail?: (error: string) => any,
    ) => void,
  ) {}
}

export default {
  authUrl,
  bind,
  qrOption,
  oauth,
} as SocialLoginApi;
