<template>
  <div id="qr">此处会显示二维码</div>
</template>
<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import { WxLogin } from "../../utils/WxLogin";
@Component({
  components: {},
})
export default class QRLogin extends Vue {
  mounted(): void {
    this.$api.wechatApi.socialLogin.qrOption(
      { id: "qr", state: "state" },
      ({ data }) => {
        WxLogin(data);
      }
    );
  }
}
</script>
<style lang="less" scoped></style>
