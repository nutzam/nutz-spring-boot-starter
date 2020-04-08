import Vue from 'vue';
import moment from 'moment';
import 'moment/locale/zh-cn';
moment.locale('zh-cn');

Vue.filter('NumberFormat', function (value: number) {
  console.log(value);
  if (!value) {
    return '0';
  }
  const intPartFormat = value.toString().replace(/(\d)(?=(?:\d{3})+$)/g, '$1,'); // 将整数部分逢三一断
  return intPartFormat;
});

Vue.filter('dayjs', function (
  dataStr: string,
  pattern: string = 'YYYY-MM-DD HH:mm:ss'
) {
  return moment(dataStr).format(pattern);
});

Vue.filter('moment', function (
  dataStr: string,
  pattern: string = 'YYYY-MM-DD HH:mm:ss'
) {
  return moment(dataStr).format(pattern);
});
