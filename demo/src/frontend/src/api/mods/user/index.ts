/**
 * @description User Controller
 */
import add from './add';
import edit from './edit';
import get from './get';
import remove from './remove';
import grant from './grant';
import grantInfo, {GrantInfoParams} from './grantInfo';
import roles from './roles';
import grantRoles from './grantRoles';
import permissions from './permissions';
import search, {SearchParams} from './search';

export class UserApi {
  constructor(
    public add: (
      user: defs.User,

      success?: ({
        data,
        ext,
        state,
        errors,
      }: {
        data: defs.User;
        ext: ObjectMap;
        state: 'SUCCESS' | 'FAIL' | 'EXCEPTION';
        errors?: Array<string>;
      }) => any,
      fail?: (error: string) => any
    ) => void,

    public edit: (
      user: defs.User,

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
        data: defs.User;
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

    public roles: (
      id: number,

      success?: ({
        data,
        ext,
        state,
        errors,
      }: {
        data: Array<defs.RoleInfo>;
        ext: ObjectMap;
        state: 'SUCCESS' | 'FAIL' | 'EXCEPTION';
        errors?: Array<string>;
      }) => any,
      fail?: (error: string) => any
    ) => void,

    public grantRoles: (
      id: number,
      roles: Array<number>,

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
        data: defs.Pager<defs.User>;
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
  roles,
  grantRoles,
  permissions,
  search,
} as UserApi;
