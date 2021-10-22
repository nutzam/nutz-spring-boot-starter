/* eslint-disable @typescript-eslint/no-unused-vars */
/* eslint-disable @typescript-eslint/no-explicit-any */

declare namespace dictionary {
  export class Codebook {
    /** createTime */
    createTime?: string;

    /** creater */
    creater?: string;

    /** 描述 */
    description?: string;

    /** 禁用标识 */
    disabled: boolean;

    /** 分组Id */
    groupId: number;

    /** id */
    id?: number;

    /** 序号 */
    index?: number;

    /** key */
    key: string;

    /** 上级Key */
    parentKey?: number;

    /** updateTime */
    updateTime?: string;

    /** updater */
    updater?: string;

    /** value */
    value: string;
  }

  export class Group {
    /** createTime */
    createTime?: string;

    /** creater */
    creater?: string;

    /** 分组描述 */
    description?: string;

    /** 禁用标识 */
    disabled: boolean;

    /** id */
    id?: number;

    /** 分组唯一键 */
    key: string;

    /** 分组名称 */
    name: string;

    /** updateTime */
    updateTime?: string;

    /** updater */
    updater?: string;
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
