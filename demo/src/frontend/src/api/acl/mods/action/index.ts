/* eslint-disable @typescript-eslint/no-explicit-any */
/**
 * @description 功能动作模块
 */
import add from './add';
import edit from './edit';
import get from './get';
import remove from './remove';

export class ActionApi {
  constructor(
    public add: (
      action: acl.Action,

      success?: ({
        data,
        ext,
        state,
        errors,
      }: {
        data: acl.Action;
        ext: ObjectMap;
        state: 'SUCCESS' | 'FAIL' | 'EXCEPTION';
        errors?: Array<string>;
      }) => any,
      fail?: (error: string) => any,
    ) => void,

    public edit: (
      action: acl.Action,

      success?: ({
        data,
        ext,
        state,
        errors,
      }: {
        data: void;
        ext: ObjectMap;
        state: 'SUCCESS' | 'FAIL' | 'EXCEPTION';
        errors?: Array<string>;
      }) => any,
      fail?: (error: string) => any,
    ) => void,

    public get: (
      id: number,

      success?: ({
        data,
        ext,
        state,
        errors,
      }: {
        data: acl.Action;
        ext: ObjectMap;
        state: 'SUCCESS' | 'FAIL' | 'EXCEPTION';
        errors?: Array<string>;
      }) => any,
      fail?: (error: string) => any,
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
        state: 'SUCCESS' | 'FAIL' | 'EXCEPTION';
        errors?: Array<string>;
      }) => any,
      fail?: (error: string) => any,
    ) => void,
  ) {}
}

export default {
  add,
  edit,
  get,
  remove,
} as ActionApi;
