/**
 * @description Code Book Controller
 */
import add from './add';
import edit from './edit';
import vxeSave from './vxeSave';
import get from './get';
import remove from './remove';
import byGroup from './byGroup';

export class CodeBookApi {
  constructor(
    public add: (
      codeBook: defs.CodeBook,

      success?: ({
        data,
        ext,
        state,
        errors,
      }: {
        data: defs.CodeBook;
        ext: ObjectMap;
        state: 'SUCCESS' | 'FAIL' | 'EXCEPTION';
        errors?: Array<string>;
      }) => any,
      fail?: (error: string) => any
    ) => void,

    public edit: (
      codeBook: defs.CodeBook,

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

    public vxeSave: (
      vxeData: defs.VXETableSaveDTO<defs.CodeBook>,

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
        data: defs.CodeBook;
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

    public byGroup: (
      id: number,

      success?: ({
        data,
        ext,
        state,
        errors,
      }: {
        data: Array<defs.CodeBook>;
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
  vxeSave,
  get,
  remove,
  byGroup,
} as CodeBookApi;
