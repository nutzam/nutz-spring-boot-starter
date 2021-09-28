import { PluginObject } from "vue";
import axios, {
  AxiosRequestConfig,
  AxiosInstance,
  AxiosResponse,
  AxiosError,
} from "axios";
import settings from "@/core/config";
import store from "@/store";
import { notification } from "ant-design-vue";
import { i18nRender } from "@/locales/index";

const errorTitle = i18nRender("global.http.error");
const forbidden = i18nRender("global.http.forbidden");
const notFount = i18nRender("global.http.notFount");
const configError = i18nRender("global.http.configError");
export function defaultSuccess(data: unknown): void {
  console.log(data);
}
export function defaultError(error: string): void {
  notification.error({
    message: errorTitle.toString(),
    description: error,
  });
}
export function msgAndLogout(msg: string): void {
  console.log(msg);
}

const config = {
  baseURL: settings.http.prefix,
  timeout: settings.http.timeout,
};
const _axios = axios.create(config);

_axios.interceptors.request.use(
  (cfg: AxiosRequestConfig) => {
    const token = store && store.getters.token;
    if (token) {
      cfg.headers.Authorization = token;
    }
    return cfg;
  },
  (err: unknown) => {
    console.warn(err);
    return Promise.reject(configError);
  }
);
// Add a response interceptor
_axios.interceptors.response.use(
  (response: AxiosResponse) => {
    if (response.data.state == "SUCCESS") {
      //数据成功
      return Promise.resolve(response.data);
    } else {
      //数据失败直接reject
      return Promise.reject(response.data.errors[0]);
    }
  },
  (error: AxiosError<acl.Result<unknown>>) => {
    switch (Number(error.response && error.response.status)) {
      case 403 | 401:
        msgAndLogout(forbidden.toString());
        break;
      case 404:
        return Promise.reject(notFount);
      case 500:
      default:
        return Promise.reject(
          error.response &&
            error.response.data.errors &&
            error.response.data.errors[0]
        );
    }
  }
);

const Plugin: PluginObject<AxiosInstance> = {
  install: (Vue) => {
    Object.defineProperties(Vue.prototype, {
      $axios: {
        get() {
          return _axios;
        },
      },
    });
  },
};

export { _axios as http, Plugin as axiosPlugin };
