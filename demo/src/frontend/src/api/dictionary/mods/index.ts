import codebook, { CodebookApi } from './codebook';

import group, { GroupApi } from './group';

export class DictionaryApi {
  constructor(public codebook: CodebookApi, public group: GroupApi) {}
}

export default {
  codebook,
  group,
} as DictionaryApi;
