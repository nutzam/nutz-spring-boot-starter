export function buttonFormRule() {
  return [
    {
      type: 'input',
      field: 'key',
      title: 'Key',
      info: '',
      props: {
        placeholder: '请输入按钮Key',
        clearable: true,
      },
      _fc_drag_tag: 'input',
      hidden: false,
      display: true,
      $required: '请输入按钮Key',
      validate: [
        {
          trigger: 'change',
          mode: 'required',
          message: '请输入按钮Key',
          required: true,
          type: 'string',
        },
      ],
    },
    {
      type: 'input',
      field: 'name',
      title: '按钮名称',
      info: '',
      props: {
        placeholder: '请输入按钮Key',
        clearable: true,
      },
      _fc_drag_tag: 'input',
      hidden: false,
      display: true,
      $required: '请输入按钮名称',
      validate: [
        {
          trigger: 'change',
          mode: 'required',
          message: '请输入按钮名称',
          required: true,
          type: 'string',
        },
      ],
    },
    {
      type: 'input',
      field: 'description',
      title: '按钮描述',
      info: '',
      props: {
        placeholder: '请输入按钮描述',
        type: 'textarea',
        showWordLimit: true,
        rows: 3,
        resize: 'both',
      },
      _fc_drag_tag: 'input',
      hidden: false,
      display: true,
    },
    {
      type: 'hidden',
      field: 'id',
    },
    {
      type: 'hidden',
      field: 'menuKey',
    },
    {
      type: 'hidden',
      field: 'clientId',
    },
  ];
}
