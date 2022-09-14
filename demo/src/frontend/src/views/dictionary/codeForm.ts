export function codeFormRule() {
  return [
    {
      type: 'input',
      field: 'key',
      title: '数据key',
      info: '',
      props: {
        placeholder: '请填写数据key',
        clearable: true,
      },
      _fc_drag_tag: 'input',
      hidden: false,
      display: true,
      $required: '请填写数据key',
      validate: [
        {
          trigger: 'change',
          mode: 'required',
          message: '请填写数据key',
          required: true,
          type: 'string',
        },
      ],
    },
    {
      type: 'input',
      field: 'value',
      title: '数据值',
      info: '',
      props: {
        placeholder: '请填写数据值',
        clearable: true,
      },
      _fc_drag_tag: 'input',
      hidden: false,
      display: true,
      $required: '请填写数据值',
      validate: [
        {
          trigger: 'change',
          mode: 'required',
          message: '请填写数据值',
          required: true,
          type: 'string',
        },
      ],
    },
    {
      type: 'inputNumber',
      field: 'index',
      title: '数据排序',
      value: 1,
      info: '',
      props: {
        min: 1,
        step: 1,
        stepStrictly: true,
        placeholder: '请填写数据排序',
      },
      _fc_drag_tag: 'inputNumber',
      hidden: false,
      display: true,
      $required: '请填写数据排序',
      validate: [
        {
          trigger: 'change',
          mode: 'required',
          message: '请输入数据排序',
          required: true,
          type: 'number',
        },
      ],
    },
    {
      type: 'input',
      field: 'description',
      title: '数据描述',
      info: '',
      props: {
        placeholder: '请填写分组描述',
        clearable: true,
        type: 'textarea',
        rows: 3,
      },
      _fc_drag_tag: 'input',
      hidden: false,
      display: true,
    },
    {
      type: 'switch',
      field: 'disabled',
      title: '禁用',
      info: '',
      props: {
        checkedChildren: '禁用',
        unCheckedChildren: '启用',
      },
      _fc_drag_tag: 'switch',
      hidden: false,
      display: true,
    },
    {
      type: 'hidden',
      field: 'id',
    },
    {
      type: 'hidden',
      field: 'parentKey',
    },
    {
      type: 'hidden',
      field: 'groupKey',
    },
  ];
}
