/**
 * @description Test Controller
 */
import jsr303 from './jsr303';
import actions, {ActionsParams} from './actions';

export class TestApi {
  constructor(
    public jsr303: (
      idCard: defs.TestIdCard,

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

      params: ActionsParams,
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
    ) => void
  ) {}
}

export default {
  jsr303,
  actions,
} as TestApi;
