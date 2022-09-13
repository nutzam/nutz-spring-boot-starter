export function menuFormRule() {
  return [
    {
      type: 'input',
      field: 'key',
      title: 'Key',
      info: '',
      props: {
        placeholder: '请输入菜单Key',
        clearable: true,
      },
      _fc_drag_tag: 'input',
      hidden: false,
      display: true,
      $required: '请输入菜单Key',
      validate: [
        {
          trigger: 'change',
          mode: 'required',
          message: '请输入菜单Key',
          required: true,
          type: 'string',
        },
      ],
    },
    {
      type: 'input',
      field: 'name',
      title: '菜单名称',
      info: '',
      props: {
        placeholder: '请输入菜单Key',
        clearable: true,
      },
      _fc_drag_tag: 'input',
      hidden: false,
      display: true,
      $required: '请输入菜单名称',
      validate: [
        {
          trigger: 'change',
          mode: 'required',
          message: '请输入菜单名称',
          required: true,
          type: 'string',
        },
      ],
    },
    {
      type: 'input',
      field: 'description',
      title: '菜单描述',
      info: '',
      props: {
        placeholder: '请输入菜单描述',
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
      field: 'parentMenuKey',
    },
  ];
}
