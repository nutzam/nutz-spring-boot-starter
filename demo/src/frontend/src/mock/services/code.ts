import Mock from 'mockjs2';
import {Result} from '../util';
Mock.mock(RegExp('/code/book/all*'), 'get', (option: any) => {
  console.log(option);
  return Mock.mock(
    Result.success({
      name: '@cname',
    })
  );
});
