export interface Pager {
  pageNumber: number;
  pageSize: number;
  pageCount: number;
  recordCount: number;
}

export interface User {
  userName: string;
  mobile: string;
  id: number;
  createTime: string;
  permissionInfo?: Array<{
    moduleName: string;
    actions: Array<{
      id: number;
      key: string;
      name: string;
      selected?: boolean;
    }>;
  }>;
}
export interface Role {
  key: string;
  name: string;
  description: string;
  id: number;
  createTime: string;
  selected?: boolean;
  permissionInfo?: Array<{
    moduleName: string;
    actions: Array<Action>;
  }>;
}

export interface Module {
  key: string;
  moduleKey?: string;
  name: string;
  moduleName?: string;
  descr: string;
  id: number;
  moduleId?: number;
  createTime: string;
  selected?: boolean;
  actions?: Array<Action>;
}
export interface Action {
  id?: number;
  key?: string;
  name?: string;
  moduleId: number;
  selected?: boolean;
  installed?: boolean;
}

export interface Group {
  id: number | null;
  key: string;
  name: string;
  description?: string;
}
export interface CodeBook {
  id: number | null;
  groupId: number;
  parentId: number;
  index?: number;
  key: string;
  value: string;
  description?: string;
  children?: Array<CodeBook>;
}
