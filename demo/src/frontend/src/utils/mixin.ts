import {Vue, Component} from 'vue-property-decorator';
import {deviceEnquire, DEVICE_TYPE} from '@/utils/device';
import {AppModule} from '@/store/modules/app';
import {UserModule} from '../store/modules/user';
import defaultImg from '@/assets/logo.png';

@Component
class Mixin extends Vue {
  public AppModule = AppModule;
  public UserModule = UserModule;
  public isTopMenu() {
    return AppModule.layoutMode === 'topmenu';
  }
  public isSideMenu() {
    return !this.isTopMenu();
  }
}

@Component
class DeviceMixin extends Vue {
  public AppModule = AppModule;
  public UserModule = UserModule;
  public isMobile(): boolean {
    return AppModule.device === DEVICE_TYPE.MOBILE;
  }
  public isDesktop(): boolean {
    return AppModule.device === DEVICE_TYPE.DESKTOP;
  }
  public isTablet(): boolean {
    return AppModule.device === DEVICE_TYPE.TABLET;
  }
  public toImgUrl(imgKey: string) {
    if (imgKey) return `http://image.kerbores.com/${imgKey}`;
    return defaultImg;
  }
}

@Component
class AppDeviceEnquireMixin extends Vue {
  mounted() {
    deviceEnquire((deviceType: string) => {
      switch (deviceType) {
        case DEVICE_TYPE.DESKTOP:
          AppModule.TOGGLE_DEVICE(DEVICE_TYPE.DESKTOP);
          AppModule.SetSidebar(true);
          break;
        case DEVICE_TYPE.TABLET:
          AppModule.TOGGLE_DEVICE(DEVICE_TYPE.TABLET);
          AppModule.SetSidebar(false);
          break;
        case DEVICE_TYPE.MOBILE:
        default:
          AppModule.TOGGLE_DEVICE(DEVICE_TYPE.MOBILE);
          AppModule.SetSidebar(true);
          break;
      }
    });
  }
}

export {Mixin, DeviceMixin, AppDeviceEnquireMixin};
