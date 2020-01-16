const globalHeader = {
  message: '正在编译语言！',
  themeMessage: '正在编译主题！',
};
const settingDrawer = {
  pageStyleTitle: '整体风格设置',
  dartTooltipTitle: '暗色菜单风格',
  lightTooltipTitle: '亮色菜单风格',
  themeTitle: '主题色',
  layoutTitle: '导航模式',
  sideMenuTitle: '侧边菜单布局',
  topMenuTitle: '顶部菜单布局',
  dustRed: '薄暮',
  volcano: '火山',
  sunsetOrange: '日暮',
  cyan: '明青',
  polarGreen: '极光绿',
  daybreakBlue: '拂晓蓝（默认）',
  geekBlue: '极客蓝',
  goldenPurple: '酱紫',
  contentWidth: '内容区域宽度',
  fixed: '固定',
  flux: '流式',
  fixHeader: '固定顶部导航',
  effectiveWhenFixHeader: '固定顶部导航时有效',
  hideHeaderWhenSliding: '下滑时隐藏顶部导航',
  fixedSideMenu: '固定侧边菜单',
  other: '其他设置',
  colorWeakMode: '色弱模式',
  multiTabMode: '多页签模式',
};
const menu = {
  index: '首页',
  demo: '演示',
  about: '关于',
  dashboard: '仪表盘',
  analysis: '分析页',
  form: '表单',
  basicForm: '基础表单',
  stepForm: '分步表单',
  acl: '访问控制',
  code: '码本管理',
  codeBook: '码本数据',
  userList: '用户列表',
  roleList: '角色列表',
  moduleList: '模块列表',
};

const http = {
  networkError: '网络错误',
  networkErrorDesc: '请检查网络连接是否异常',
  timeoutError: '请求超时',
  timeoutErrorDesc: '请求已超时，请稍后重试',
  forbidden: '禁止访问该资源',
  error: '错误提示',
  configError: '配置错误',
  notFount: '请求的资源不存在',
};

const multitab = {
  leftHasNoTag: '左侧没有标签',
  rightHasNoTag: '右侧没有标签',
  closecurrent: '关闭当前标签',
  closecright: '关闭右侧标签',
  closecleft: '关闭左侧标签',
  closecall: '关闭全部标签',
};

const notice = {
  notice: '通知',
  message: '消息',
  todo: '待办',
};

const header = {
  userCenter: '个人中心',
  settings: '账户设置',
  logout: '退出登录',
  confirm: '确认退出',
  confirmMessage: '您确认要退出当前用户?',
  ok: '确定',
  cancle: '取消',
};
const page = {
  user: {
    name: '用户名',
    mobile: '手机号码',
    createTime: '创建时间',
    action: '操作',
    add: '添加用户',
    delete: '删除',
    grant: '授权',
    role: '设置角色',
    edit: '编辑',
    search: {
      placeholder: '请输入关键字',
    },
    confirm: {
      title: '确认删除这个用户?',
      textYes: '确认',
      textNo: '取消',
    },
    message: {
      deleteSuccess: '删除用户成功',
    },
  },
  role: {
    key: '角色Key',
    name: '角色名',
    createTime: '创建时间',
    action: '操作',
    add: '添加角色',
    delete: '删除',
    edit: '编辑',
    grant: '授权',
    search: {
      placeholder: '请输入关键字',
    },
    confirm: {
      title: '确认删除这个角色?',
      textYes: '确认',
      textNo: '取消',
    },
    message: {
      deleteSuccess: '删除角色成功',
    },
  },
  module: {
    key: '模块Key',
    name: '模块名称',
    createTime: '创建时间',
    action: '操作',
    add: '添加角色',
    delete: '删除',
    edit: '编辑',
    search: {
      placeholder: '请输入关键字',
    },
    confirm: {
      title: '确认删除这个模块?',
      textYes: '确认',
      textNo: '取消',
    },
    message: {
      deleteSuccess: '删除角色模块',
    },
  },
};

export {
  globalHeader,
  settingDrawer,
  menu,
  http,
  multitab,
  notice,
  header,
  page,
};
