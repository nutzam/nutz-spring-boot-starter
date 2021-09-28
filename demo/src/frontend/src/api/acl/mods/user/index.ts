/* eslint-disable @typescript-eslint/no-explicit-any */
/**
 * @description 用户模块
 */
import add from "./add";
import edit from "./edit";
import get from "./get";
import remove from "./remove";
import grant from "./grant";
import grantInfo, { GrantInfoParams } from "./grantInfo";
import roles from "./roles";
import grantRoles from "./grantRoles";
import permissions from "./permissions";
import search, { SearchParams } from "./search";

export class UserApi {
  constructor(
    public add: (
      user: acl.User,

      success?: ({
        data,
        ext,
        state,
        errors,
      }: {
        data: acl.User;
        ext: ObjectMap;
        state: "SUCCESS" | "FAIL" | "EXCEPTION";
        errors?: Array<string>;
      }) => any,
      fail?: (error: string) => any
    ) => void,

    public edit: (
      user: acl.User,

      success?: ({
        data,
        ext,
        state,
        errors,
      }: {
        data: void;
        ext: ObjectMap;
        state: "SUCCESS" | "FAIL" | "EXCEPTION";
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
        data: acl.User;
        ext: ObjectMap;
        state: "SUCCESS" | "FAIL" | "EXCEPTION";
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
        data: void;
        ext: ObjectMap;
        state: "SUCCESS" | "FAIL" | "EXCEPTION";
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
        data: void;
        ext: ObjectMap;
        state: "SUCCESS" | "FAIL" | "EXCEPTION";
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
        data: Array<acl.ModuleInfo>;
        ext: ObjectMap;
        state: "SUCCESS" | "FAIL" | "EXCEPTION";
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
        data: Array<acl.RoleInfo>;
        ext: ObjectMap;
        state: "SUCCESS" | "FAIL" | "EXCEPTION";
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
        data: void;
        ext: ObjectMap;
        state: "SUCCESS" | "FAIL" | "EXCEPTION";
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
        data: acl.PermissionInfo;
        ext: ObjectMap;
        state: "SUCCESS" | "FAIL" | "EXCEPTION";
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
        data: acl.Pagination<acl.User>;
        ext: ObjectMap;
        state: "SUCCESS" | "FAIL" | "EXCEPTION";
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
