/* eslint-disable @typescript-eslint/no-explicit-any */
export function WxLogin(a: wechat.WxLogin): void {
  let c = "default";
  a.self_redirect === !0
    ? (c = "true")
    : a.self_redirect === !1 && (c = "false");
  const d = document.createElement("iframe");
  let e =
    "https://open.weixin.qq.com/connect/qrconnect?appid=" +
    a.appid +
    "&scope=" +
    a.scope +
    "&redirect_uri=" +
    a.redirect_uri +
    "&state=" +
    a.state +
    "&login_type=jssdk&self_redirect=" +
    c;
  e += a.style ? "&style=" + a.style : "";
  e += a.href ? "&href=" + a.href : "";
  (d.src = e), (d.frameBorder = "0");
  (d as any).allowTransparency = "true";
  d.scrolling = "no";
  d.width = "300px";
  d.height = "400px";
  const f = document.getElementById(a.id);
  (f as HTMLElement).innerHTML = "";
  f && f.appendChild(d);
}
