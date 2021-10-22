<template>
  <div>微信授权登录</div>
</template>
<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import qs from "qs";
@Component({
  components: {},
})
export default class WechatLogin extends Vue {
  mounted(): void {
    const code = qs.parse(location.search.substr(1)).code;
    console.log(code);
    //TODO 根据code调用后台的接口,如果返回token就直接登录成功返回进来之前的页面,如果返回openid就进行绑定操作
    this.$api.wechatApi.socialLogin.oauth(
      "WECHAT_SCAN",
      { code: code + "" },
      ({ data }) => {
        console.log(data); //token
      },
      (data) => {
        console.log(data); // openid 显示绑定页面
      }
    );
  }
}
</script>
<style lang="less" scoped></style>
