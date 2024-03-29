import validators from "@/core/validators";
export default [
  {
    type: "input",
    field: "name",
    title: "用户名",
    info: "",
    props: {
      type: "text",
      minlength: 3,
      maxlength: 15,
      placeholder: "请输入用户名",
      clearable: true,
    },
    _fc_drag_tag: "input",
    hidden: false,
    display: true,
    validate: [
      {
        trigger: "change",
        mode: "required",
        message: "请输入用户名",
        required: true,
        type: "string",
      },
      {
        trigger: "change",
        mode: "min",
        message: "用户名不少于3个字符",
        min: 3,
        type: "string",
      },
    ],
  },
  {
    type: "input",
    field: "password",
    title: "密码",
    info: "",
    props: {
      type: "password",
      maxlength: 16,
      minlength: 8,
      placeholder: "请输入用户密码",
      clearable: true,
      showPassword: true,
    },
    _fc_drag_tag: "input",
    hidden: false,
    display: true,
    validate: [
      {
        trigger: "change",
        mode: "required",
        message: "请输入用户密码",
        required: true,
        type: "string",
      },
      {
        trigger: "change",
        mode: "min",
        message: "密码不少于8位",
        min: 8,
        type: "string",
      },
      {
        trigger: "change",
        mode: "max",
        message: "密码长度不超过16位",
        max: 16,
        type: "string",
      },
    ],
  },
  {
    type: "input",
    field: "mobile",
    title: "手机号",
    info: "",
    props: {
      type: "text",
      maxlength: 11,
      minlength: 11,
      placeholder: "请输入手机号码",
    },
    _fc_drag_tag: "input",
    hidden: false,
    display: true,
    validate: [
      {
        trigger: "change",
        mode: "required",
        message: "请输入手机号码",
        required: true,
        type: "string",
      },
      {
        trigger: "change",
        mode: "pattern",
        message: "请输入正确的手机号码",
        pattern: validators.mobile,
        type: "string",
      },
    ],
  },
];
