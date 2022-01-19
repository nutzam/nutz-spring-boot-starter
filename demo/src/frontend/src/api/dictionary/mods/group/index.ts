/* eslint-disable @typescript-eslint/no-explicit-any */
/**
 * @description 码本分组模块
 */
import add from './add';
import edit from './edit';
import get from './get';
import remove from './remove';
import search, { SearchParams } from './search';

export class GroupApi {
  constructor(
    public add: (
      group: dictionary.Group,

      success?: ({
        data,
        ext,
        state,
        errors,
      }: {
        data: dictionary.Group;
        ext: ObjectMap;
        state: 'SUCCESS' | 'FAIL' | 'EXCEPTION';
        errors?: Array<string>;
      }) => any,
      fail?: (error: string) => any,
    ) => void,

    public edit: (
      group: dictionary.Group,

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
        data: dictionary.Group;
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

    public search: (
      params: SearchParams,
      success?: ({
        data,
        ext,
        state,
        errors,
      }: {
        data: dictionary.Pagination<dictionary.Group>;
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
  search,
} as GroupApi;
