/**
 * @description Captcha Controller
 */
import captcha, {CaptchaParams} from './captcha';

export class CaptchaApi {
  constructor(
    public captcha: (
      params: CaptchaParams,
      success?: ({
        data,
        ext,
        state,
        errors,
      }: {
        data: string;
        ext: ObjectMap;
        state: 'SUCCESS' | 'FAIL' | 'EXCEPTION';
        errors?: Array<string>;
      }) => any,
      fail?: (error: string) => any
    ) => void
  ) {}
}

export default {
  captcha,
} as CaptchaApi;
