/* eslint-disable @typescript-eslint/no-explicit-any */
export interface Columns {
  title: string | any;
  dataIndex: string;
  align: string;
  width?: string;
  scopedSlots: { customRender: string };
  key?: string;
  ellipsis?: boolean;
  sorter?: boolean;
  filters?: any;
  fixed?: string;
}

export interface UserInfo {
  id: number;
  password?: string;
  name?: string;
  mobile?: string;
  updateTime?: string;
  createTime?: string;
  permissionInfo?: Array<acl.ModuleInfo>;
}
export interface RoleInfo {
  createTime?: string;
  description?: string;
  id?: number;
  key?: string;
  name?: string;
  permissionInfo?: Array<acl.ModuleInfo>;
  updateTime?: string;
}

export interface ModuleInfo {
  actions?: Array<acl.Action>;
  descr?: string;
  moduleId?: number;
  moduleKey: string;
  moduleName: string;
  selected?: boolean;
}

export interface AntPagination {
  size?: string;
  total?: number;
  current?: number;
  pageSize?: number;
  showSizeChanger?: boolean;
  showQuickJumper?: boolean;
  hideOnSinglePage?: boolean;
  simple?: boolean;
}

export interface FormCol {
  xs: Span;
  sm: Span;
}
export interface FormValue {
  key?: string;
  name?: string;
  id?: number;
}
export interface FormErrors {
  name?: {
    errors?: any[];
  };
}
