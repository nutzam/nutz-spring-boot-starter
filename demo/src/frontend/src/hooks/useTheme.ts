import { useAppStore } from './../store/app';
import { onBeforeMount } from 'vue';
import { ConfigProvider } from 'ant-design-vue';

export const colorList: Array<{ key: string; color: string; checked?: boolean }> = [
  {
    key: 'dust', //dust
    color: '#F5222D',
  },
  {
    key: 'volcano', //volcano'火山'
    color: '#FA541C',
  },
  {
    key: 'sunset', //'日暮'sunset
    color: '#FAAD14',
  },
  {
    key: 'cyan', //'明青'cyan
    color: '#13C2C2',
  },
  {
    key: 'polar', //'极光绿'polar
    color: '#52C41A',
  },
  {
    key: 'daybreak', //'拂晓蓝(默认'
    color: '#19ACE9',
  },
  {
    key: 'geek', //'极客蓝'geek
    color: '#2F54EB',
  },
  {
    key: 'purple', //purple'酱紫'
    color: '#722ED1',
  },
];

export const colors: string[] = colorList.map(c => c.color);

export const useUserTheme = () => {
  onBeforeMount(() => {
    apply(load());
  });
};

export const randomTheme = (): string => {
  const i = Math.floor(Math.random() * 10);
  return colors[i];
};

export const load = () => {
  return useAppStore().theme.primaryColor;
};

export const save = (color: string) => {
  useAppStore().changePrimaryColor(color);
};

export const apply = (color: string) => {
  ConfigProvider.config({
    theme: {
      primaryColor: color,
    },
  });
  save(color);
};
