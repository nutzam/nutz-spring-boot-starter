/**
 * @description 码本数据
 *
 */
import saveOrUpdateDictionary from './saveOrUpdateDictionary';
import dictionaryDetail from './dictionaryDetail';
import deleteDictionary from './deleteDictionary';
import dictionarys from './dictionarys';
import type { Pagination } from '@/api/api';

export interface DictionarysParams {
  /** 页码 */
  page?: number;
  /** 页面大小 */
  size?: number;
  /** 搜索关键词 */
  key?: string;
}

export interface DictionaryApi {
  /** 增加/编辑码本数据 */
  saveOrUpdateDictionary: (
    /** 请求体 */
    requestBody: code.Dictionary,

    success?: (data: code.Dictionary) => void,
    fail?: (error: string) => void,
  ) => void;

  /** 码本数据详情 */
  dictionaryDetail: (
    /** 码本数据id */
    id: number,

    success?: (data: code.Dictionary) => void,
    fail?: (error: string) => void,
  ) => void;

  /** 删除码本数据 */
  deleteDictionary: (
    /** 码本数据id */
    id: number,

    success?: (data: void) => void,
    fail?: (error: string) => void,
  ) => void;

  /** 分页查询码本数据 */
  dictionarys: (
    params: DictionarysParams,
    success?: (data: Pagination<code.Dictionary>) => void,
    fail?: (error: string) => void,
  ) => void;
}
export default {
  saveOrUpdateDictionary,
  dictionaryDetail,
  deleteDictionary,
  dictionarys,
} as DictionaryApi;
