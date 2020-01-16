import {check} from '@/utils/auth';
import {VueConstructor} from 'vue';

interface IOptions {
  name: string;
}
function install(
  Vue: VueConstructor,
  options: IOptions = {name: 'auth'}
): void {
  Vue.directive(options.name || 'auth', {
    inserted(el, binding) {
      if (!check(binding.value)) {
        console.log('remove');
        el.parentNode && el.parentNode.removeChild(el);
      }
    },
  });
}

export default {install};
