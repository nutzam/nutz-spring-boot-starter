/**
 * @description Module Controller
 */
import add from './add';
import edit from './edit';
import get from './get';
import remove from './remove';
import actions from './actions';
import search, {SearchParams} from './search';

export class ModuleApi {
  constructor(
    public add: (
      module: defs.Module,

      success?: ({
        data,
        ext,
        state,
        errors,
      }: {
        data: defs.Module;
        ext: ObjectMap;
        state: 'SUCCESS' | 'FAIL' | 'EXCEPTION';
        errors?: Array<string>;
      }) => any,
      fail?: (error: string) => any
    ) => void,

    public edit: (
      module: defs.Module,

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
        data: defs.Module;
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

    public actions: (
      id: number,

      success?: ({
        data,
        ext,
        state,
        errors,
      }: {
        data: Array<defs.Action>;
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
        data: defs.Pager<defs.Module>;
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
  actions,
  search,
} as ModuleApi;
