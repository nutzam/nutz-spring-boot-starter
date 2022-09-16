export interface Layout {
  showDrawer: boolean;
  collapsed: boolean;
  collapsedWidth: number;
  contentWidth: 'Fixed' | 'Fluid';
  disableContentMargin: boolean;
  disableMobile: boolean;
  fixSiderbar: boolean;
  fixedHeader: boolean;
  headerHeight: number;
  headerTheme: 'light' | 'dark';
  iconfontUrl: string;
  uuid: string;
  layout: 'side' | 'top' | 'mix';
  navTheme: 'light' | 'dark';
  waterMark: string;
  splitMenus: boolean;
  headerRender?: boolean;
  footerRender?: boolean;
  menu?: boolean;
  menuHeaderRender?: boolean;
}

export interface Theme {
  menuColor: string; // primary color of ant design
  primaryColor: string;
  colorWeak: boolean;
}

export interface GlobalConfig {
  layout: Layout;
  theme: Theme;
  language: string;
  name: string;
  title: string;
  description: string;
  copyright: string;
  enableI8n: boolean;
}

export const config: GlobalConfig = {
  layout: {
    showDrawer: true,
    collapsed: false,
    collapsedWidth: 48,
    contentWidth: 'Fluid',
    disableContentMargin: true,
    disableMobile: false,
    fixSiderbar: true,
    fixedHeader: true,
    headerHeight: 48,
    headerTheme: 'dark',
    iconfontUrl: '//at.alicdn.com/t/c/font_3644733_mrstfnf0dyn.js',
    uuid: '',
    layout: 'mix',
    navTheme: 'dark',
    waterMark: 'NUTZ 示例',
    splitMenus: true,
    headerRender: true,
    footerRender: true,
    menu: true,
    menuHeaderRender: true,
  },
  theme: {
    menuColor: '#015b8a',
    primaryColor: '#19ACE9', //4e8fad
    colorWeak: false,
  },
  language: 'zh_CN',
  name: 'nutz-demo',
  title: 'NUTZ 示例',
  description:
    'Nutz,Nutz 是一个新兴的开源项目。它没有过去，只有未来，我可以保证，在未来，Nutz 的代码 绝对不会膨胀。 所有的功能设计的出发点就是最大限度给予程序员实惠。',
  copyright: 'Copyright © 2022  demo.nutz.cn',
  enableI8n: true,
};
