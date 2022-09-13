import type { App } from 'vue';
import formCreate from '@form-create/ant-design-vue';
import {
  AutoComplete,
  Button,
  Cascader,
  Checkbox,
  Col,
  DatePicker,
  Form,
  Input,
  Modal,
  Popover,
  Radio,
  Rate,
  Row,
  Select,
  Slider,
  TimePicker,
  Tooltip,
  Tree,
  TreeSelect,
  Upload,
  Switch,
  InputNumber,
} from 'ant-design-vue';
import 'ant-design-vue/dist/antd.less';
export const formCreateComponentInsatll = {
  install: (app: App) => {
    app.component(Form.name) || app.use(Form);
    app.component(Input.name) || app.use(Input);
    app.component(Number.name) || app.use(Number);
    app.component(AutoComplete.name) || app.use(AutoComplete);
    app.component(Cascader.name) || app.use(Cascader);
    app.component(Checkbox.name) || app.use(Checkbox);
    app.component(Radio.name) || app.use(Radio);
    app.component(DatePicker.name) || app.use(DatePicker);
    app.component(TimePicker.name) || app.use(TimePicker);
    app.component(TreeSelect.name) || app.use(TreeSelect);
    app.component(TreeSelect.name) || app.use(TreeSelect);
    app.component(Col.name) || app.component(Col.name, Col);
    app.component(Row.name) || app.component(Row.name, Row);
    app.component(Button.name) || app.use(Button);
    app.component(Rate.name) || app.use(Rate);
    app.component(Select.name) || app.use(Select);
    app.component(Slider.name) || app.use(Slider);
    app.component(Switch.name) || app.use(Switch);
    app.component(Upload.name) || app.use(Upload);
    app.component(Tree.name) || app.use(Tree);
    app.component(Tooltip.name) || app.use(Tooltip);
    app.component(Popover.name) || app.use(Popover);
    app.component(Modal.name) || app.use(Modal);
    app.component(InputNumber.name) || app.use(InputNumber);
  },
};

export default {
  install: (app: App) => {
    app.use(formCreate).use(formCreateComponentInsatll);
  },
};
