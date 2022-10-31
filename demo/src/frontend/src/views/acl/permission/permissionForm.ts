import type { Codebook } from '@/api/api';

export function permissionFormRule(types: Array<Codebook>) {
  return [
    {
      type: 'radio',
      field: 'type',
      title: '类型',
      info: '',
      effect: {
        fetch: '',
      },
      props: {
        _optionType: 2,
        optionType: 'button',
        buttonStyle: 'solid',
      },
      options: types.map(t => {
        return { value: t.name, label: t.description };
      }),
      _fc_drag_tag: 'radio',
      hidden: false,
      display: true,
      $required: '',
      validate: [
        {
          trigger: 'change',
          mode: 'required',
          message: '请选择类型',
          required: true,
          type: 'string',
        },
      ],
    },
    {
      type: 'input',
      field: 'parentKey',
      title: '上级Key',
      info: '',
      props: {
        maxlength: 15,
        minlength: 3,
        placeholder: '请输入上级Key',
        clearable: true,
      },
      _fc_drag_tag: 'input',
      hidden: false,
      display: true,
      $required: false,
    },
    {
      type: 'input',
      field: 'key',
      title: 'Key',
      info: '',
      props: {
        maxlength: 15,
        minlength: 3,
        placeholder: '请输入Key',
        clearable: true,
      },
      _fc_drag_tag: 'input',
      hidden: false,
      display: true,
      $required: '请输入Key',
      validate: [
        {
          trigger: 'change',
          mode: 'required',
          message: '请输入Key',
          required: true,
          type: 'string',
        },
      ],
    },
    {
      type: 'input',
      field: 'name',
      title: '名称',
      info: '',
      props: {
        maxlength: 15,
        minlength: 2,
        placeholder: '请输入权限名称',
        clearable: true,
      },
      _fc_drag_tag: 'input',
      hidden: false,
      display: true,
      $required: '请输入权限名称',
      validate: [
        {
          trigger: 'change',
          mode: 'required',
          message: '请输入名称',
          required: true,
          type: 'string',
        },
        {
          trigger: 'change',
          mode: 'len',
          message: '请输入名称',
          len: 15,
          type: 'string',
        },
      ],
    },
    {
      type: 'input',
      field: 'description',
      title: '描述',
      info: '',
      props: {
        type: 'textarea',
        maxlength: 50,
        minlength: 0,
        showWordLimit: true,
        placeholder: '请输入权限描述',
        clearable: true,
        rows: 3,
      },
      _fc_drag_tag: 'input',
      hidden: false,
      display: true,
    },
    {
      type: 'hidden',
      field: 'id',
    },
  ];
}
