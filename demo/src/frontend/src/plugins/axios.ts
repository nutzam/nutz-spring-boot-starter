import { notification } from 'ant-design-vue';
// import { messages } from '@/locales';
import axios, { type AxiosError, type AxiosRequestConfig, type AxiosResponse } from 'axios';
import type { App } from 'vue';
import { http } from '@/core/http';
import i18n from '@/locales';
const { t } = i18n.global;

export interface GlobalError {
  /** 错误码 */
  code: number;
  /** 错误信息 */
  message: string;
}

notification.config({
  placement: 'bottomRight',
  bottom: '50px',
  duration: 3,
});

export function defaultSuccess(data: unknown): void {
  console.log(data);
}
export function defaultError(error: string): void {
  notification.error({
    message: t('notification.message.error'),
    description: error,
  });
}
export function msgAndLogout(msg: string): void {
  console.log(msg);
}
const config = {
  baseURL: http.prefix,
  timeout: http.timeout,
};
const _axios = axios.create(config);
_axios.interceptors.request.use(
  (cfg: AxiosRequestConfig) => {
    const token = ''; //TODO token
    if (token) {
      cfg.headers && (cfg.headers.Authorization = token);
    }
    return cfg;
  },
  (err: unknown) => {
    console.warn(err);
    return Promise.reject(t('error.config'));
  },
);

_axios.interceptors.response.use(
  (response: AxiosResponse) => {
    // 数据成功
    return Promise.resolve(response);
  },
  (error: AxiosError<GlobalError>) => {
    switch (Number(error.response && error.response.status)) {
      case 403 | 401:
        msgAndLogout(t('error.forbidden'));
        break;
      case 404: {
        return Promise.reject(t('error.notFount'));
      }
      case 500:
      default:
        return Promise.reject(error.response && error.response.data.message);
    }
  },
);
export { _axios as http };

export default {
  install: (app: App) => {
    app.config.globalProperties.$http = _axios;
  },
};
