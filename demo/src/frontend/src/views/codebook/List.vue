<template>
  <div>
    <a-row :gutter="16">
      <a-col :span="7">
        <GroupList @groupClicked="groupClicked"></GroupList>
      </a-col>
      <a-col :span="17">
        <DataList ref="dataList"></DataList>
      </a-col>
    </a-row>
  </div>
</template>
<script lang="ts">
import {Component, Prop, Vue} from 'vue-property-decorator';
import GroupList from './Group.vue';
import DataList from './DataList.vue';
@Component({
  components: {GroupList, DataList},
})
export default class CodeBook extends Vue {
  groupClicked(id: number) {
    (this.$refs.dataList as DataList).load(id);
  }
}
</script>
<style lang="less" scoped></style>
