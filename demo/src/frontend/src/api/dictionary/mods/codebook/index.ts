/* eslint-disable @typescript-eslint/no-explicit-any */
/**
 * @description 码本数据模块
 */
import add from './add';
import edit from './edit';
import vxeSave from './vxeSave';
import get from './get';
import remove from './remove';
import byGroup from './byGroup';

export class CodebookApi {
  constructor(
    public add: (
      codebook: dictionary.Codebook,

      success?: ({
        data,
        ext,
        state,
        errors,
      }: {
        data: dictionary.Codebook;
        ext: ObjectMap;
        state: 'SUCCESS' | 'FAIL' | 'EXCEPTION';
        errors?: Array<string>;
      }) => any,
      fail?: (error: string) => any,
    ) => void,

    public edit: (
      codebook: dictionary.Codebook,

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

    public vxeSave: (
      vxeData: dictionary.VXETableSaveDTO<dictionary.Codebook>,

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
        data: dictionary.Codebook;
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

    public byGroup: (
      id: number,

      success?: ({
        data,
        ext,
        state,
        errors,
      }: {
        data: Array<dictionary.Codebook>;
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
  vxeSave,
  get,
  remove,
  byGroup,
} as CodebookApi;
