export function groupFormRule() {
  return [
    {
      type: 'input',
      field: 'name',
      title: '分组名称',
      info: '',
      props: {
        placeholder: '请填写分组名称',
        clearable: true,
      },
      _fc_drag_tag: 'input',
      hidden: false,
      display: true,
      $required: '请填写分组名称',
      validate: [
        {
          trigger: 'change',
          mode: 'required',
          message: '请填写分组名称',
          required: true,
          type: 'string',
        },
      ],
    },
    {
      type: 'input',
      field: 'description',
      title: '分组描述',
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
      field: 'key',
    },
    {
      type: 'hidden',
      field: 'id',
    },
  ];
}
