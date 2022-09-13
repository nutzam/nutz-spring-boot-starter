/**
 * @description 码本分组
 *
 */
import saveOrUpdateGroup from './saveOrUpdateGroup';
import groupDetail from './groupDetail';
import deleteGroup from './deleteGroup';
import groups from './groups';
import type { Pagination } from '@/api/api';

export interface GroupsParams {
  /** 页码 */
  page?: number;
  /** 页面大小 */
  size?: number;
  /** 搜索关键词 */
  key?: string;
}

export interface GroupApi {
  /** 增加/编辑码本分组 */
  saveOrUpdateGroup: (
    /** 请求体 */
    requestBody: code.Group,

    success?: (data: code.Group) => void,
    fail?: (error: string) => void,
  ) => void;

  /** 码本分组详情 */
  groupDetail: (
    /** 码本分组id */
    id: number,

    success?: (data: code.Group) => void,
    fail?: (error: string) => void,
  ) => void;

  /** 删除码本分组 */
  deleteGroup: (
    /** 码本分组id */
    id: number,

    success?: (data: void) => void,
    fail?: (error: string) => void,
  ) => void;

  /** 分页查询码本分组 */
  groups: (
    params: GroupsParams,
    success?: (data: Pagination<code.Group>) => void,
    fail?: (error: string) => void,
  ) => void;
}
export default {
  saveOrUpdateGroup,
  groupDetail,
  deleteGroup,
  groups,
} as GroupApi;
