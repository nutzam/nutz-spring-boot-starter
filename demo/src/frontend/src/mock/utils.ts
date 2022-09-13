import Mock from 'mockjs';

export const Response = {
  successData: (data: unknown) => {
    return data;
  },
  success: (template: unknown) => {
    return Mock.mock(template);
  },
  successList: (template: unknown) => {
    return Mock.mock(template).list;
  },
  successPage: (template: unknown, current: number, size: number) => {
    const records = Mock.mock(template).list;
    const pages = 5; // 总页数
    return {
      current, // 当前页面
      pages, // 总页数
      records,
      size, // 分页大小
      total: (pages - 1) * size + records.length, // 数据总条数
    };
  },
  fail: () => {
    return Mock.mock({
      'errors|1-3': ['@csentence()'],
    }).errors;
  },
};
