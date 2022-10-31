/**
 * mybatis-plus 分页对象
 */
export interface IPage<T> {
  /**
   * 当前页面
   */
  current: number;
  /**
   * 总页数
   */
  pages?: number;
  /**
   * 数据记录
   */
  records?: Array<T>;
  /**
   * 分页大小
   */
  size: number;
  /**
   * 数据总条数
   */
  total?: number;
}
/**
 * Nutz 分页对象
 */
export interface Pagination<T> {
  /**
   * 数据记录
   */
  dataList: Array<T>;
  /**
   * 是否首页
   */
  first?: boolean;
  /**
   * 是否尾页
   */
  last?: boolean;
  /**
   * 偏移量
   */
  offset?: number;
  /**
   * 总页数
   */
  pageCount: number;
  /**
   * 当前页码
   */
  pageNumber: number;
  /**
   * 页面大小
   */
  pageSize: number;
  /**
   * sql参数
   */
  paras: Record<string, unknown>;
  /**
   * 数据记录总数
   */
  recordCount?: number;
}
/**
 * VXE 数据结构
 */
export interface VXETableSaveDTO<T> {
  /**
   * 新增记录数据
   */
  insertRecords: Array<T>;
  /**
   *  删除记录数据
   */
  removeRecords: Array<T>;
  /**
   * 更新记录数据
   */
  updateRecords: Array<T>;
}
/**
 * 枚举码本
 */
export interface Codebook {
  /** 码本编码 */
  code: string;

  /** 码本名称 */
  description: string;

  /** 码本值 */
  name: string;
}

/**
 * 全局错误
 */
export interface GlobalError {
  /** 错误码 */
  code?: number;

  /** 错误信息 */
  message?: string;
}
/**
 * NutMap
 */
type NutMap<Key extends string, Value = unknown> = {
  [key in Key]: Value;
};
// 导入其他模块
/// <reference path="./acl/api.d.ts" />
/// <reference path="./auth/api.d.ts" />
/// <reference path="./code/api.d.ts" />
