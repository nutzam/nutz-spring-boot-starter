<template>
  <div class="main">
    <a-form
      id="formLogin"
      class="user-layout-login"
      ref="formLogin"
      :form="form"
      @submit="handleSubmit"
    >
      <a-tabs
        :active-key="customActiveKey"
        :tab-bar-style="{textAlign: 'center', borderBottom: 'unset'}"
        @change="handleTabClick"
      >
        <a-tab-pane key="tab1" tab="账号密码登录">
          <a-form-item>
            <a-input
              size="large"
              type="text"
              placeholder="请输入用户名"
              v-decorator="[
                'name',
                {
                  rules: [
                    {required: true, message: '请输入用户名'},
                    {validator: handleUsernameOrEmail},
                  ],
                  validateTrigger: 'change',
                },
              ]"
            >
              <a-icon
                slot="prefix"
                type="user"
                :style="{color: 'rgba(0,0,0,.25)'}"
              />
            </a-input>
          </a-form-item>

          <a-form-item>
            <a-input
              size="large"
              type="password"
              autocomplete="false"
              placeholder="请输入密码"
              v-decorator="[
                'password',
                {
                  rules: [{required: true, message: '请输入密码'}],
                  validateTrigger: 'blur',
                },
              ]"
            >
              <a-icon
                slot="prefix"
                type="lock"
                :style="{color: 'rgba(0,0,0,.25)'}"
              />
            </a-input>
          </a-form-item>

          <a-form-item>
            <a-input
              size="large"
              type="text"
              autocomplete="false"
              placeholder="请输入验证码"
              v-decorator="[
                'captcha',
                {
                  rules: [{required: true, message: '请输入验证码'}],
                  validateTrigger: 'blur',
                },
              ]"
            >
              <a-icon
                slot="prefix"
                type="picture"
                :style="{color: 'rgba(0,0,0,.25)'}"
              />
              <img
                slot="suffix"
                class="captcha"
                title="点击刷新验证码"
                :src="captcha || defaultCaptcha"
                @click="loadCaptcha"
              />
            </a-input>
          </a-form-item>
        </a-tab-pane>

        <a-tab-pane key="tab2" tab="手机号登录">
          <a-form-item>
            <a-input
              size="large"
              type="text"
              placeholder="请输入手机号码"
              v-decorator="[
                'mobile',
                {
                  rules: [
                    {
                      required: true,
                      pattern: /^1[3456789]\d{9}$/,
                      message: '请输入正确的手机号',
                    },
                  ],
                  validateTrigger: 'change',
                },
              ]"
            >
              <a-icon
                slot="prefix"
                type="mobile"
                :style="{color: 'rgba(0,0,0,.25)'}"
              />
            </a-input>
          </a-form-item>

          <a-row :gutter="16">
            <a-col class="gutter-row" :span="16">
              <a-form-item>
                <a-input
                  size="large"
                  type="text"
                  placeholder="请输入验证码"
                  v-decorator="[
                    'captcha',
                    {
                      rules: [{required: true, message: '请输入验证码'}],
                      validateTrigger: 'blur',
                    },
                  ]"
                >
                  <a-icon
                    slot="prefix"
                    type="mail"
                    :style="{color: 'rgba(0,0,0,.25)'}"
                  />
                </a-input>
              </a-form-item>
            </a-col>
            <a-col class="gutter-row" :span="8">
              <a-button
                class="getCaptcha"
                tabindex="-1"
                :disabled="state.smsSendBtn"
                v-text="
                  (!state.smsSendBtn && '获取验证码') || state.time + ' s'
                "
              ></a-button>
            </a-col>
          </a-row>
        </a-tab-pane>
      </a-tabs>
      <a-form-item>
        <a-checkbox checked v-decorator="['rememberMe']">自动登录</a-checkbox>
      </a-form-item>

      <a-form-item style="margin-top: 24px;">
        <a-button
          size="large"
          type="primary"
          html-type="submit"
          class="login-button"
          :loading="state.loginBtn"
          :disabled="state.loginBtn"
          >确定</a-button
        >
      </a-form-item>

      <div class="user-login-other">
        <span>其他登录方式</span>
        <a>
          <a-icon class="item-icon" type="alipay-circle"></a-icon>
        </a>
        <a>
          <a-icon class="item-icon" type="taobao-circle"></a-icon>
        </a>
        <a>
          <a-icon class="item-icon" type="weibo-circle"></a-icon>
        </a>
      </div>
    </a-form>
  </div>
</template>
<script lang="ts">
import {Component, Prop, Vue, Mixins} from 'vue-property-decorator';
import {Mutation} from 'vuex-class';
import {Mixin} from '../../utils/mixin';
import axios from 'axios';
import defaultCaptcha from '@/assets/captcha.png';
@Component({
  components: {},
})
export default class Login extends Mixins(Mixin) {
  public form: any;
  public customActiveKey: string = 'tab1';

  public captcha: string = '';

  public uuid!: string;
  public defaultCaptcha: any = defaultCaptcha;

  public state: any = {
    time: 60,
    loginBtn: false,
    loginType: 0,
    smsSendBtn: false,
  };

  created() {
    this.form = this.$form.createForm(this);
    this.loadCaptcha();
  }

  public loadCaptcha() {
    this.$api.captcha.captcha({length: 4}, ({data: captcha, ext: {uuid}}) => {
      this.captcha = captcha;
      this.uuid = uuid;
    });
  }

  handleSubmit(e: any) {
    e.preventDefault();
    const {
      form: {validateFields},
      state,
      customActiveKey,
    } = this;
    state.loginBtn = true;
    const validateFieldsKey = ['name', 'password', 'captcha'];
    validateFields(
      validateFieldsKey,
      {force: true},
      (err: string, values: any) => {
        if (!err) {
          this.doLogin(Object.assign(values, {uuid: this.uuid}));
        } else {
          setTimeout(() => {
            state.loginBtn = false;
          }, 600);
        }
      }
    );
  }
  doLogin(values: any) {
    this.$api.auth.login(
      values,
      ({data: user}) => {
        var subject: defs.User =
          user.extInfo != undefined
            ? (user.extInfo.user as any)
            : {userName: ''};
        this.UserModule.login({
          name: subject.userName,
          welcome: '',
          avatar: '',
          token: user.token || '',
          roles: user.roles || [],
          permissions: user.permissions || [],
        });
        this.$router.push({path: '/index'});
      },
      (error: string) => {
        this.$notification.error({
          message: '登录失败',
          description: error,
        });
        setTimeout(() => {
          this.state.loginBtn = false;
        }, 10000);
      }
    );
  }
  handleTabClick(key: string) {
    if (key === 'tab1') this.customActiveKey = key;
    this.customActiveKey = 'tab1';
  }
  handleUsernameOrEmail(rule: string, value: string, callback: () => any) {
    const {state} = this;
    const regex = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
    if (regex.test(value)) {
      state.loginType = 0;
    } else {
      state.loginType = 1;
    }
    callback();
  }
}
</script>
<style lang="less" scoped>
.ant-tabs,
.ant-checkbox-wrapper,
.ant-form {
  color: #d9d9d9;
}
.user-layout-login {
  label {
    font-size: 14px;
  }

  .getCaptcha {
    display: block;
    width: 100%;
    height: 40px;
  }

  .forge-password {
    font-size: 14px;
  }

  button.login-button {
    padding: 0 15px;
    font-size: 16px;
    height: 40px;
    width: 100%;
  }

  .user-login-other {
    text-align: left;
    margin-top: 24px;
    line-height: 22px;

    .item-icon {
      font-size: 24px;
      color: rgba(250, 250, 250, 0.836);
      margin-left: 16px;
      vertical-align: middle;
      cursor: pointer;
      transition: color 0.3s;

      &:hover {
        color: #1890ff;
      }
    }

    .register {
      float: right;
    }
  }
}
.captcha {
  max-height: 38px;
  margin-right: -20px;
  cursor: pointer;
}
</style>
