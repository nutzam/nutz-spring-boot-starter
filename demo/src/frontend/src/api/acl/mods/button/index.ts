/**
 * @description 操作按钮
 *
 */
import saveOrUpdateButton from './saveOrUpdateButton';
import buttonDetail from './buttonDetail';
import deleteButton from './deleteButton';
import buttons from './buttons';
import type { Pagination } from '@/api/api';

export interface ButtonsParams {
  /** 页码 */
  page?: number;
  /** 页面大小 */
  size?: number;
  /** 搜索关键词 */
  key?: string;
}

export interface ButtonApi {
  /** 增加/编辑操作按钮 */
  saveOrUpdateButton: (
    /** 请求体 */
    requestBody: acl.Button,

    success?: (data: acl.Button) => void,
    fail?: (error: string) => void,
  ) => void;

  /** 操作按钮详情 */
  buttonDetail: (
    /** 操作按钮id */
    id: number,

    success?: (data: acl.Button) => void,
    fail?: (error: string) => void,
  ) => void;

  /** 删除操作按钮 */
  deleteButton: (
    /** 操作按钮id */
    id: number,

    success?: (data: void) => void,
    fail?: (error: string) => void,
  ) => void;

  /** 分页查询操作按钮 */
  buttons: (
    params: ButtonsParams,
    success?: (data: Pagination<acl.Button>) => void,
    fail?: (error: string) => void,
  ) => void;
}
export default {
  saveOrUpdateButton,
  buttonDetail,
  deleteButton,
  buttons,
} as ButtonApi;
