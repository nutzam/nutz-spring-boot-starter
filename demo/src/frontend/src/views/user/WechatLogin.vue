<template>
  <div v-if="socialLoginBindDTO.openid">
    <!-- 此处显示绑定表单-->
    <input name="mobile" type="text" v-model="socialLoginBindDTO.mobile" />
    <input name="code" type="text" v-model="socialLoginBindDTO.code" />
    <button type="submit" @click="bind">绑定</button>
  </div>
  <div v-else>正在进行微信登录检查....</div>
</template>
<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import qs from "qs";
@Component({
  components: {},
})
export default class WechatLogin extends Vue {
  socialLoginBindDTO: wechat.TieSocialLoginUserDataObject = {
    channel: "MP",
    code: "",
    openid: "",
    mobile: "",
  };
  state!: string;
  mounted(): void {
    const code = qs.parse(location.search.substr(1)).code;
    const state = qs.parse(location.search.substr(1)).state;

    this.state = state + "";
    this.$api.wechatApi.socialLogin.oauth(
      "WECHAT_SCAN",
      { code: code + "" },
      ({ data }) => {
        console.log(data); //token
        //TODO 将token放入状态管理,然后根据state或者是进入此页面前保存的状态跳回原来的页面(恢复登录前现场)
      },
      (data) => {
        this.socialLoginBindDTO.openid = data;
      }
    );
  }

  bind(): void {
    this.$api.wechatApi.socialLogin.bind(
      this.socialLoginBindDTO,
      ({ data }) => {
        console.log(data); //到这里的就是一定拿到token了
        //TODO 将token放入状态管理,然后根据state或者是进入此页面前保存的状态跳回原来的页面(恢复登录前现场)
      }
    );
  }
}
</script>
<style lang="less" scoped></style>
