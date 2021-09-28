<template>
  <div>
    <a-row :gutter="16">
      <a-col :span="6">
        <GroupList @groupClicked="groupClicked"></GroupList>
      </a-col>
      <a-col :span="18">
        <CodebookList ref="codebookList"></CodebookList>
      </a-col>
    </a-row>
  </div>
</template>
<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import GroupList from "./GroupList.vue";
import CodebookList from "./CodebookList.vue";
@Component({
  components: { GroupList, CodebookList },
})
export default class Dictionary extends Vue {
  groupClicked(group: dictionary.Group): void {
    (this.$refs.codebookList as CodebookList).load(group);
  }
}
</script>
<style lang="less" scoped></style>
