declare namespace code {
  /**
   * 码本数据
   */
  export interface Dictionary {
    /** createdTime */
    createdTime?: string;

    /** 描述 */
    description?: string;

    /** 禁用标识 */
    disabled?: boolean;

    /** 分组Key */
    groupKey: string;

    /** id */
    id?: number;

    /** 序号 */
    index: number;

    /** key */
    key: string;

    /** 上级Key */
    parentKey?: string;

    /** updatedTime */
    updatedTime?: string;

    /** value */
    value: string;
  }

  /**
   * 码本分组
   */
  export interface Group {
    /** createdTime */
    createdTime?: string;

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

    /** updatedTime */
    updatedTime?: string;
  }
}
