/* eslint-disable @typescript-eslint/no-explicit-any */
/**
 * @description 功能模块
 */
import add from "./add";
import edit from "./edit";
import get from "./get";
import remove from "./remove";
import actions from "./actions";
import search, { SearchParams } from "./search";

export class ModuleApi {
  constructor(
    public add: (
      module: acl.Module,

      success?: ({
        data,
        ext,
        state,
        errors,
      }: {
        data: acl.Module;
        ext: ObjectMap;
        state: "SUCCESS" | "FAIL" | "EXCEPTION";
        errors?: Array<string>;
      }) => any,
      fail?: (error: string) => any
    ) => void,

    public edit: (
      module: acl.Module,

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
        data: acl.Module;
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

    public actions: (
      id: number,

      success?: ({
        data,
        ext,
        state,
        errors,
      }: {
        data: Array<acl.Action>;
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
        data: acl.Pagination<acl.Module>;
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
  actions,
  search,
} as ModuleApi;
