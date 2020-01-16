<template>
  <div class="antd-pro-components-article-list-content-index-listContent">
    <div class="description">
      <slot>
        {{ description }}
      </slot>
    </div>
    <div class="extra">
      <a-avatar :src="avatar" size="small" />
      <a :href="href">{{ owner }}</a> 发布在 <a :href="href">{{ href }}</a>
      <em>{{ updateAt | moment }}</em>
    </div>
  </div>
</template>
<script lang="ts">
import {Component, Prop, Vue} from 'vue-property-decorator';
@Component({
  components: {},
})
export default class ArticleListContent extends Vue {
  @Prop({
    type: String,
    default: 'antd-pro-components-article-list-content-index-listContent',
  })
  private prefixCls!: string;

  @Prop({
    type: String,
    default: '',
  })
  private description!: string;

  @Prop({
    type: String,
    required: true,
  })
  private owner!: string;

  @Prop({
    type: String,
    required: true,
  })
  private avatar!: string;

  @Prop({
    type: String,
    required: true,
  })
  private href!: string;

  @Prop({
    type: String,
    required: true,
  })
  private updateAt!: string;
}
</script>
<style lang="less" scoped>
@import '../index.less';

.antd-pro-components-article-list-content-index-listContent {
  .description {
    max-width: 720px;
    line-height: 22px;
  }
  .extra {
    margin-top: 16px;
    color: @text-color-secondary;
    line-height: 22px;

    & /deep/ .ant-avatar {
      position: relative;
      top: 1px;
      width: 20px;
      height: 20px;
      margin-right: 8px;
      vertical-align: top;
    }

    & > em {
      margin-left: 16px;
      color: @disabled-color;
      font-style: normal;
    }
  }
}

@media screen and (max-width: @screen-xs) {
  .antd-pro-components-article-list-content-index-listContent {
    .extra {
      & > em {
        display: block;
        margin-top: 8px;
        margin-left: 0;
      }
    }
  }
}
</style>
