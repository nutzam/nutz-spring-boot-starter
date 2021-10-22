/* eslint-disable @typescript-eslint/no-unused-vars */
/* eslint-disable @typescript-eslint/no-explicit-any */

declare namespace wechat {
  export class TieSocialLoginUserDataObject {
    /** 渠道 */
    channel: 'MP' | 'MINIAPP' | 'WECHAT_SCAN' | 'WECHAT';

    /** 验证码 */
    code: string;

    /** 手机号 */
    mobile: string;

    /** openid */
    openid: string;
  }

  export class WxLogin {
    /** appid */
    appid: string;

    /** href */
    href?: string;

    /** id */
    id: string;

    /** redirect_uri */
    redirect_uri: string;

    /** scope */
    scope: string;

    /** self_redirect */
    self_redirect: boolean;

    /** state */
    state: string;

    /** style */
    style?: 'black' | 'white';
  }

  export class Pagination<T0 = any> {
    /** dataList */
    dataList: Array<T0>;

    /** first */
    first?: boolean;

    /** last */
    last?: boolean;

    /** offset */
    offset?: number;

    /** pageCount */
    pageCount?: number;

    /** pageNumber */
    pageNumber?: number;

    /** pageSize */
    pageSize?: number;

    /** paras */
    paras?: ObjectMap<any, any>;

    /** recordCount */
    recordCount?: number;
  }

  export class Result<T0 = any> {
    /** 响应数据 */
    data: T0;

    /** 错误信息列表 */
    errors?: Array<string>;

    /** 响应扩展数据 */
    ext?: ObjectMap<any, any>;

    /** 响应状态 */
    state: 'SUCCESS' | 'FAIL' | 'EXCEPTION';

    /** success */
    success?: boolean;
  }

  export class VXETableSaveDTO<T0 = any> {
    /** 新增记录数据 */
    insertRecords: Array<T0>;

    /** 删除记录数据 */
    removeRecords: Array<T0>;

    /** 更新记录数据 */
    updateRecords: Array<T0>;
  }
}
