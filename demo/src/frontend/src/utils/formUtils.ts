/* eslint-disable @typescript-eslint/no-explicit-any */
const fixFormOption = (oldOption: Array<any>): Array<any> => {
  if (!oldOption) {
    return [];
  }
  return oldOption.map((e: any) => {
    const props = e.props;
    // 事件注入参数默认值由 false 改为 true
    if (e.inject !== false) {
      e.inject = true;
    }
    if (props) {
      // 多选
      if (props.multiple) {
        props.mode = 'multiple';
      }
      // 搜索
      if (props.filterable) {
        props['show-search'] = true;
        props['filter-option'] = (input: string, option: { label: string }) => {
          const label = option.label;
          if (!label) {
            return false;
          }
          return !input || label.includes(input);
        };
      }
    }
    return e;
  });
};
export const formUtils = {
  fixFormOption,
};
