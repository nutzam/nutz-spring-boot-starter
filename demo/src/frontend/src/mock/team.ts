import { Random } from 'mockjs';
import type { MockMethod } from 'vite-plugin-mock';
import { Response } from './utils';
export const teamListMock = [
  {
    url: '/api/project/teams',
    method: 'get',
    response: () => {
      return Response.successList({
        'list|10-40': [
          {
            id: '@id',
            name: '@ctitle',
            description: '@cparagraph',
            members: Random.integer(5, 15),
            repositories: Random.integer(5, 15),
          },
        ],
      });
    },
  },
] as MockMethod[];
