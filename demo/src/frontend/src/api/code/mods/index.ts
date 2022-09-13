import dictionary, { type DictionaryApi } from './dictionary';

import group, { type GroupApi } from './group';

export interface CodeApi {
  dictionary: DictionaryApi;
  group: GroupApi;
}

export default {
  dictionary,
  group,
} as CodeApi;
