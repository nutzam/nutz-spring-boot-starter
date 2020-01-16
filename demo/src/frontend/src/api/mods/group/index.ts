/**
 * @description Group Controller
 */
import add from './add';
import edit from './edit';
import get from './get';
import remove from './remove';
import search, {SearchParams} from './search';

export class GroupApi {
  constructor(
    public add: (
      group: defs.Group,

      success?: ({
        data,
        ext,
        state,
        errors,
      }: {
        data: defs.Group;
        ext: ObjectMap;
        state: 'SUCCESS' | 'FAIL' | 'EXCEPTION';
        errors?: Array<string>;
      }) => any,
      fail?: (error: string) => any
    ) => void,

    public edit: (
      group: defs.Group,

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
        data: defs.Group;
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

    public search: (
      params: SearchParams,
      success?: ({
        data,
        ext,
        state,
        errors,
      }: {
        data: defs.Pager<defs.Group>;
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
  search,
} as GroupApi;
