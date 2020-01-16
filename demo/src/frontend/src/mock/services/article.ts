import Mock from 'mockjs2';
import {Result} from '../util';
Mock.mock(RegExp('/article/all*'), 'get', (option: any) => {
  console.log(option);
  return Mock.mock(
    Result.success({
      'articles|5-10': [
        {
          thumb: {
            'sharp|1': ['circular', ''],
            image: Mock.Random.dataImage('200x200'),
          },
          icon: 'user',
          title: '@ctitle',
          description: '@cparagraph',
          'metas|2-3': [
            {
              content: '@cword(3,5)',
              'divider|1': true,
            },
          ],
        },
      ],
    })
  );
});
