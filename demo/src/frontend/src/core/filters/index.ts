import Vue from "vue";
import moment from "moment";
import "moment/locale/zh-cn";
moment.locale("zh-cn");

Vue.filter("NumberFormat", function (value: number) {
  if (!value) {
    return "0";
  }
  // 将整数部分逢三一段添加千分位
  return value.toString().replace(/(\d)(?=(?:\d{3})+$)/g, "$1,");
});

Vue.filter("dayjs", function (dataStr: string, pattern?: string) {
  pattern = pattern ? pattern : "YYYY-MM-DD HH:mm:ss";
  return moment(dataStr).format(pattern);
});

Vue.filter("moment", function (dataStr: string, pattern?: string) {
  pattern = pattern ? pattern : "YYYY-MM-DD HH:mm:ss";
  return moment(dataStr).format(pattern);
});
