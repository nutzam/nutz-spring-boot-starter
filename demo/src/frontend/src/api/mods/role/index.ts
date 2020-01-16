/**
 * @description Role Controller
 */
import add from './add';
import edit from './edit';
import get from './get';
import remove from './remove';
import grant from './grant';
import grantInfo, {GrantInfoParams} from './grantInfo';
import permissions from './permissions';
import search, {SearchParams} from './search';

export class RoleApi {
  constructor(
    public add: (
      role: defs.Role,

      success?: ({
        data,
        ext,
        state,
        errors,
      }: {
        data: defs.Role;
        ext: ObjectMap;
        state: 'SUCCESS' | 'FAIL' | 'EXCEPTION';
        errors?: Array<string>;
      }) => any,
      fail?: (error: string) => any
    ) => void,

    public edit: (
      role: defs.Role,

      success?: ({
        data,
        ext,
        state,
        errors,
      }: {
        data: any;
        ext: ObjectMap;
        state: 'SUCCESS' | 'FAIL' | 'EXCEPTION';
        errors?: Array<string>;
      }) => any,
      fail?: (error: string) => any
    ) => void,

    public get: (
      id: number,

      success?: ({
        data,
        ext,
        state,
        errors,
      }: {
        data: defs.Role;
        ext: ObjectMap;
        state: 'SUCCESS' | 'FAIL' | 'EXCEPTION';
        errors?: Array<string>;
      }) => any,
      fail?: (error: string) => any
    ) => void,

    public remove: (
      id: number,

      success?: ({
        data,
        ext,
        state,
        errors,
      }: {
        data: any;
        ext: ObjectMap;
        state: 'SUCCESS' | 'FAIL' | 'EXCEPTION';
        errors?: Array<string>;
      }) => any,
      fail?: (error: string) => any
    ) => void,

    public grant: (
      id: number,
      actionInfos: Array<string>,

      success?: ({
        data,
        ext,
        state,
        errors,
      }: {
        data: any;
        ext: ObjectMap;
        state: 'SUCCESS' | 'FAIL' | 'EXCEPTION';
        errors?: Array<string>;
      }) => any,
      fail?: (error: string) => any
    ) => void,

    public grantInfo: (
      id: number,

      params: GrantInfoParams,
      success?: ({
        data,
        ext,
        state,
        errors,
      }: {
        data: Array<defs.ModuleInfo>;
        ext: ObjectMap;
        state: 'SUCCESS' | 'FAIL' | 'EXCEPTION';
        errors?: Array<string>;
      }) => any,
      fail?: (error: string) => any
    ) => void,

    public permissions: (
      id: number,

      success?: ({
        data,
        ext,
        state,
        errors,
      }: {
        data: defs.PermissionInfo;
        ext: ObjectMap;
        state: 'SUCCESS' | 'FAIL' | 'EXCEPTION';
        errors?: Array<string>;
      }) => any,
      fail?: (error: string) => any
    ) => void,

    public search: (
      params: SearchParams,
      success?: ({
        data,
        ext,
        state,
        errors,
      }: {
        data: defs.Pager<defs.Role>;
        ext: ObjectMap;
        state: 'SUCCESS' | 'FAIL' | 'EXCEPTION';
        errors?: Array<string>;
      }) => any,
      fail?: (error: string) => any
    ) => void
  ) {}
}

export default {
  add,
  edit,
  get,
  remove,
  grant,
  grantInfo,
  permissions,
  search,
} as RoleApi;
