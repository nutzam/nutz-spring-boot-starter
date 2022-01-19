import { default as wechatApi } from "@/api/wechat/mods";

/**
 * 获取网页授权地址
 *
 * @param state
 */
export function wechatLogin(state: string): void {
  wechatApi.socialLogin.authUrl({ state: state }, ({ data }) => {
    window.location.href = data;
  });
}
