import dictionary, { type DictionaryApi } from './dictionary';

export interface CodeApi {
  dictionary: DictionaryApi;
}

export default {
  dictionary,
} as CodeApi;
