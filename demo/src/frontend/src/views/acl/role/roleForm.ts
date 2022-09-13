export function roleFormRule() {
  return [
    {
      type: 'input',
      field: 'key',
      title: 'Key',
      info: '',
      props: {
        placeholder: '请输入角色Key',
        clearable: true,
      },
      _fc_drag_tag: 'input',
      hidden: false,
      display: true,
      $required: '请输入角色Key',
      validate: [
        {
          trigger: 'change',
          mode: 'required',
          message: '请输入角色Key',
          required: true,
          type: 'string',
        },
      ],
    },
    {
      type: 'input',
      field: 'name',
      title: '角色名称',
      info: '',
      props: {
        placeholder: '请输入角色Key',
        clearable: true,
      },
      _fc_drag_tag: 'input',
      hidden: false,
      display: true,
      $required: '请输入角色名称',
      validate: [
        {
          trigger: 'change',
          mode: 'required',
          message: '请输入角色名称',
          required: true,
          type: 'string',
        },
      ],
    },
    {
      type: 'input',
      field: 'description',
      title: '角色描述',
      info: '',
      props: {
        placeholder: '请输入角色描述',
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
      field: 'clientId',
    },
  ];
}
