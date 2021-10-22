/* eslint-disable @typescript-eslint/no-explicit-any */
/**
     * @desc 获取扫码登录二维码选项信息,PC端调用此接口获取显示二维码参数,然后调用 var obj = new WxLogin({
 self_redirect:true,
 id:"login_container", 
 appid: "", 
 scope: "", 
 redirect_uri: "",
  state: "",
 style: "",
 href: ""
 });显示二维码
     */
import { defaultSuccess, defaultError, http } from '@/plugins/axios';

export class QrOptionParams {
  constructor(
    public href?: string,
    public id?: string,
    public state?: string,
  ) {}
}

export default async function(
  params: QrOptionParams,
  success: ({
    data,
    ext,
    state,
    errors,
  }: {
    data: wechat.WxLogin;
    ext: ObjectMap;
    state: 'SUCCESS' | 'FAIL' | 'EXCEPTION';
    errors?: Array<string>;
  }) => void = defaultSuccess,
  fail: (error: string) => void = defaultError,
): Promise<void> {
  return http({
    method: 'get',
    url: `/social/login/qr-option`,

    params,
  })
    .then(data => success(data as any))
    .catch(error => fail(error));
}
