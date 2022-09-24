/**
 * @description 数据字典
 *
 */
import type { GlobalError } from '@/api/api';
import saveOrUpdateGroup from './saveOrUpdateGroup';
import dictionaries from './dictionaries';
import saveOrUpdateDictionary from './saveOrUpdateDictionary';
import deleteDictionary from './deleteDictionary';
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

export interface DictionaryApi {
  /** 增加/编辑码本分组 */
  saveOrUpdateGroup: (
    /** 请求体 */
    requestBody: code.Group,

    success?: (data: code.Group) => void,
    fail?: (error: GlobalError) => void,
  ) => void;

  /** 分组下的数据字典列表 */
  dictionaries: (
    /** 码本分组key */
    group: string,

    success?: (data: Array<code.Dictionary>) => void,
    fail?: (error: GlobalError) => void,
  ) => void;

  /** 增加/编辑码本数据 */
  saveOrUpdateDictionary: (
    group: string,
    /** 请求体 */
    requestBody: code.Dictionary,

    success?: (data: code.Dictionary) => void,
    fail?: (error: GlobalError) => void,
  ) => void;

  /** 删除码本数据 */
  deleteDictionary: (
    /** 码本分组key */
    group: string,
    /** 码本数据key */
    key: string,

    success?: (data: void) => void,
    fail?: (error: GlobalError) => void,
  ) => void;

  /** 码本分组详情 */
  groupDetail: (
    /** 码本分组id */
    id: number,

    success?: (data: code.Group) => void,
    fail?: (error: GlobalError) => void,
  ) => void;

  /** 删除码本分组 */
  deleteGroup: (
    /** 码本分组key */
    key: string,

    success?: (data: void) => void,
    fail?: (error: GlobalError) => void,
  ) => void;

  /** 分页查询码本分组 */
  groups: (
    params: GroupsParams,
    success?: (data: Pagination<code.Group>) => void,
    fail?: (error: GlobalError) => void,
  ) => void;
}
export default {
  saveOrUpdateGroup,
  dictionaries,
  saveOrUpdateDictionary,
  deleteDictionary,
  groupDetail,
  deleteGroup,
  groups,
} as DictionaryApi;
