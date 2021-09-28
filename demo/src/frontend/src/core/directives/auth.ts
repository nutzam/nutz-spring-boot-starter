import { check } from "@/utils/auth";
import { VueConstructor } from "vue";

interface Options {
  name: string;
}
function install(
  Vue: VueConstructor,
  options: Options = { name: "auth" }
): void {
  Vue.directive(options.name || "auth", {
    inserted(el, binding) {
      if (!check(binding.value)) {
        el.parentNode && el.parentNode.removeChild(el);
      }
    },
  });
}

export default { install };
