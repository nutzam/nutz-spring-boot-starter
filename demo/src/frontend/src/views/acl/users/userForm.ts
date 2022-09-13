import type { Codebook } from '@/api/api';

import i18n from '@/locales';
import { useAppStore } from '@/store/app';

const { t } = i18n.global;
export function getUserFormRule(sexes: Codebook[]) {
  return [
    {
      type: 'input',
      field: 'name',
      title: t('page.users.form.add.filed.name'),
      props: {
        placeholder: t('page.users.form.add.placeholder.name'),
        clearable: true,
      },
      info: '',
      _fc_drag_tag: 'input',
      hidden: false,
      display: true,
      validate: [
        {
          trigger: 'blur',
          mode: 'required',
          message: '请输入用户名',
          required: true,
          type: 'string',
        },
      ],
    },
    {
      type: 'input',
      field: 'password',
      title: t('page.users.form.add.filed.password'),
      info: '',
      props: {
        type: 'password',
        clearable: true,
        showPassword: true,
        placeholder: t('page.users.form.add.placeholder.password'),
      },
      _fc_drag_tag: 'input',
      hidden: false,
      display: true,
      $required: '请填写用户密码',
      validate: [
        {
          trigger: 'change',
          mode: 'required',
          message: '请输入密码',
          required: true,
          type: 'string',
        },
        {
          trigger: 'change',
          mode: 'min',
          message: '请输入正确的密码',
          min: 8,
          type: 'string',
        },
        {
          trigger: 'change',
          mode: 'max',
          message: '请输入正确的密码',
          max: 20,
          type: 'string',
        },
      ],
    },
    {
      type: 'input',
      field: 'fullName',
      title: t('page.users.form.add.filed.fullName'),
      info: '',
      props: {
        placeholder: t('page.users.form.add.placeholder.fullName'),
        clearable: true,
      },
      _fc_drag_tag: 'input',
      hidden: false,
      display: true,
      $required: '请填写姓名',
      validate: [
        {
          trigger: 'change',
          mode: 'required',
          message: '请填写姓名',
          required: true,
          type: 'string',
        },
      ],
    },
    {
      type: 'input',
      field: 'mobile',
      title: t('page.departmentUser.form.add.filed.mobile'),
      info: '',
      props: {
        placeholder: t('page.departmentUser.form.add.placeholder.mobile'),
        clearable: true,
      },
      _fc_drag_tag: 'input',
      hidden: false,
      display: true,
    },
    {
      type: 'input',
      field: 'email',
      title: t('page.departmentUser.form.add.filed.email'),
      info: '',
      props: {
        placeholder: t('page.departmentUser.form.add.placeholder.email'),
        clearable: true,
      },
      _fc_drag_tag: 'input',
      hidden: false,
      display: true,
      $required: '请填写电子邮箱',
      validate: [
        {
          trigger: 'change',
          mode: 'required',
          message: '请填写电子邮箱',
          required: true,
          type: 'string',
        },
        {
          trigger: 'change',
          mode: 'pattern',
          message: '请输入正确的邮箱',
          pattern: '/\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*/',
          type: 'string',
        },
      ],
    },
    {
      type: 'radio',
      field: 'sex',
      title: t('page.departmentUser.form.add.filed.sex'),
      info: '',
      props: {
        placeholder: t('page.departmentUser.form.add.placeholder.sex'),
        clearable: true,
      },
      effect: {
        fetch: '',
      },
      options: sexes.map(item => {
        return { value: item.name, label: useAppStore().isCN ? item.description : item.name };
      }),
      _fc_drag_tag: 'radio',
      hidden: false,
      display: true,
    },
    {
      type: 'hidden',
      field: 'id',
    },
  ];
}
