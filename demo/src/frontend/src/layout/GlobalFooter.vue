<template>
  <div class="footer">
    <div class="links">
      <a href="https://thunder.zhcs.club/" target="_blank">THUNDER</a>
      <a href="https://gitea.kerbores.com/kerbores/thunder" target="_blank">
        <a-icon type="github" />
      </a>
      <a href="https://ant.design/">Ant Design</a>
      <a href="https://vue.ant.design/">Vue Antd</a>
    </div>
    <div class="copyright" v-html="AppModule.copyright"></div>
  </div>
</template>

<script lang="ts">
import {Vue, Component, Mixins} from 'vue-property-decorator';
import {Mixin} from '@/utils/mixin';

@Component
export default class Footer extends Mixins(Mixin) {}
</script>
<style lang="less" scoped>
.footer {
  padding: 0 16px;
  margin: 48px 0 24px;
  text-align: center;

  .links {
    margin-bottom: 8px;

    a {
      color: rgba(0, 0, 0, 0.45);

      &:hover {
        color: rgba(0, 0, 0, 0.65);
      }

      &:not(:last-child) {
        margin-right: 40px;
      }
    }
  }
  .copyright {
    color: rgba(0, 0, 0, 0.45);
    font-size: 14px;
  }
}
</style>
