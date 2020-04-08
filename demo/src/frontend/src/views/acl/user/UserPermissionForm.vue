<template>
  <a-modal
    title="用户授权"
    :width="750"
    :visible="visible"
    @ok="handleSubmit"
    :confirm-loading="confirmLoading"
    @cancel="handleCancel"
  >
    <a-form :form="form" @submit="handleSubmit">
      <a-form-item
        label="功能操作"
        :label-col="labelCol"
        :wrapper-col="wrapperCol"
      >
        <a-transfer
          :data-source="allActions"
          :titles="['待选权限', '已选权限']"
          :filter-option="filterOption"
          show-search
          :target-keys="targetKeys"
          :selected-keys="selectedKeys"
          :render="(item) => item.title"
          @selectChange="handleSelectChange"
          @change="handleActionChange"
          :list-style="{
            width: '200px',
            height: '300px',
          }"
          v-decorator="['actionIds', {}]"
        />
      </a-form-item>
    </a-form>
  </a-modal>
</template>
<script lang="ts">
import {Component, Prop, Vue, Watch} from 'vue-property-decorator';
import {Module} from '@/types';
@Component({
  components: {},
})
export default class UserPermissionForm extends Vue {
  visible: boolean = false;
  confirmLoading: boolean = false;
  public userId: number = 0;
  public form: any;
  selectedKeys: Array<string> = [];
  targetKeys: Array<string> = [];
  modules: Array<defs.ModuleInfo> = [];
  labelCol: any = {
    xs: {span: 24},
    sm: {span: 5},
  };
  wrapperCol: any = {
    xs: {span: 24},
    sm: {span: 17},
  };

  @Watch('allActions')
  watchAllActions() {
    this.targetKeys = !this.allActions
      ? []
      : this.allActions.filter((item) => item.selected).map((item) => item.key);
  }

  get allActions() {
    if (!this.modules || !this.modules.length) return [];
    return this.modules
      .map((item) =>
        (item.actions || []).map((action) =>
          Object.assign(action, {
            mod: {
              key: item.moduleKey,
              name: item.moduleName,
              id: item.moduleId,
            },
          })
        )
      )
      .reduce((x, y) => {
        if (x == null) return y;
        return x.concat(y || []);
      })
      .map((item) => ({
        key: `${item.mod.key}.${item.key}`,
        title: `${item.mod.name}.${item.name}`,
        description: `${item.mod.name}.${item.name}`,
        selected: item.selected,
      }));
  }

  created() {
    this.form = this.$form.createForm(this);
  }

  filterOption(inputValue: any, option: any) {
    return option.title.indexOf(inputValue) > -1;
  }

  handleSelectChange(
    sourceSelectedKeys: Array<string>,
    targetSelectedKeys: Array<string>
  ) {
    this.selectedKeys = [...sourceSelectedKeys, ...targetSelectedKeys];
  }

  handleActionChange(nextTargetKeys: Array<string>) {
    this.targetKeys = nextTargetKeys;
  }

  public show(id: number) {
    this.form.resetFields();
    this.visible = true;
    this.userId = id;
    this.loadModuleInfos(id);
  }

  public loadModuleInfos(userId: number, moduleIds?: Array<number>) {
    this.$api.user.grantInfo(userId, {mids: moduleIds}, ({data: modules}) => {
      this.modules = modules;
    });
  }

  handleSubmit(e: any) {
    this.confirmLoading = true;
    this.form.validateFields((errors: any, values: any) => {
      if (!errors) {
        this.confirmLoading = true;
        this.$api.user.grant(this.userId, values.actionIds, (result) => {
          this.confirmLoading = false;
          this.$message.success('用户授权成功', 2, () => {
            this.visible = false;
            this.$emit('submited');
          });
        });
      }
    });
  }
  handleCancel(e: any) {
    this.visible = false;
  }
}
</script>
<style lang="less" scoped></style>
