import { Vue, Component } from "vue-property-decorator";
import { AppModule } from "@/store/modules/app";
import { UserModule } from "@/store/modules/user";
import defaultImg from "@/assets/logo.png";
import config from "@/core/config";
import { i18nRender } from "../locales/index";
import { TranslateResult } from "vue-i18n";

@Component
class I18nMixin extends Vue {
  get currentLang(): string {
    return AppModule.lang || config.language;
  }

  public setLang(lang: string): void {
    this.$store.dispatch("setLang", lang);
  }

  public t(key: string): TranslateResult {
    return i18nRender(key);
  }
}

@Component
class Mixin extends Vue {
  public AppModule = AppModule;
  public UserModule = UserModule;
  public isTopMenu(): boolean {
    return AppModule.layout === "topmenu";
  }
  public isSideMenu(): boolean {
    return !this.isTopMenu();
  }

  public preventDefault(e: Event): void {
    e.preventDefault();
  }
}

@Component
class DeviceMixin extends Vue {
  public AppModule = AppModule;
  public UserModule = UserModule;
  public toImgUrl(imgKey: string): string {
    if (imgKey) return `http://image.kerbores.com/${imgKey}`;
    return defaultImg;
  }
}

export { I18nMixin, Mixin, DeviceMixin };
