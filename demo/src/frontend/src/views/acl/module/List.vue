<template>
  <div>
    <a-row type="flex">
      <a-col :span="6">
        <a-input-search
          :placeholder="$t('page.module.search.placeholder')"
          v-model="key"
          @search="loadModules"
          enter-button
        />
      </a-col>
      <a-col :span="4" :offset="14">
        <a-button type="primary" icon="plus" @click="$refs.moduleForm.show()">{{
          $t('page.module.add')
        }}</a-button>
      </a-col>
    </a-row>
    <box :top="20"></box>
    <a-table
      :columns="columns"
      :data-source="pager.dataList"
      bordered
      row-key="id"
      @expand="expand"
    >
      <a-button-group slot="action" slot-scope="module" size="small">
        <a-button
          :title="$t('page.module.edit')"
          type="primary"
          icon="edit"
          @click="$refs.moduleForm.show(module)"
        />
        <a-popconfirm
          :title="$t('page.module.confirm.title')"
          @confirm="deleteModule(module.id)"
          :ok-text="$t('page.module.confirm.textYes')"
          :cancel-text="$t('page.module.confirm.textNo')"
        >
          <a-button
            :title="$t('page.module.delete')"
            type="danger"
            icon="delete"
          />
        </a-popconfirm>
      </a-button-group>

      <p slot="expandedRowRender" slot-scope="module" style="margin: 0">
        <Box :top="20" :bottom="20">{{ module.descr }}</Box>
        <a-row
          v-if="module.actions && module.actions.length"
          :gutter="24"
          :style="{marginBottom: '12px'}"
        >
          <a-col :span="24" :style="{marginBottom: '12px'}">
            <a-col :lg="4" :md="24">
              <span>功能操作：</span>
            </a-col>
            <a-col :lg="20" :md="24">
              <a-tag
                color="cyan"
                v-for="(action, k) in module.actions"
                :key="k"
                :closable="!action.installed"
                @close.stop="deleteAction($event, action)"
                @click="editAction(action)"
                >{{ action.name }}</a-tag
              >
              <a-button
                type="primary"
                icon="plus"
                size="small"
                @click="addAction(module.id)"
              ></a-button>
            </a-col>
          </a-col>
        </a-row>
      </p>
    </a-table>
    <ModuleForm ref="moduleForm" @submited="loadModules"></ModuleForm>
    <ActionForm ref="actionForm"></ActionForm>
    <div class="uuid_no_show">{{ AppModule.uuid }}</div>
  </div>
</template>
<script lang="ts">
import {Component, Prop, Vue, Mixins} from 'vue-property-decorator';
import Box from '@/components/Box';
import {Mixin} from '@/utils/mixin';
import ModuleForm from './ModuleForm.vue';
import ActionForm from './ActionForm.vue';
@Component({
  components: {Box, ModuleForm, ActionForm},
})
export default class ModuleList extends Mixins(Mixin) {
  public key: string = '';
  public pager: defs.Pager<defs.Module> = {
    pageNumber: 1,
    pageSize: 15,
    pageCount: 0,
    recordCount: 0,
    dataList: [],
  };

  get columns() {
    return [
      {title: this.$t('page.module.key'), dataIndex: 'key'},
      {title: this.$t('page.module.name'), dataIndex: 'name'},
      {title: this.$t('page.module.createTime'), dataIndex: 'createTime'},
      {
        title: this.$t('page.module.action'),
        dataIndex: '',
        scopedSlots: {customRender: 'action'},
      },
    ];
  }

  created() {
    this.loadModules();
  }

  addAction(id: number) {
    (this.$refs.actionForm as ActionForm).show({
      moduleId: id,
      key: '',
      name: '',
      installed: false,
    });
  }
  deleteAction(e: any, action: any) {
    if (!action.installed && action.id) {
      this.$api.action.remove(action.id, result => {
        this.$message.success('删除操作成功');
      });
      return;
    }
    e.preventDefault();
    this.$message.warning('内置操作不能删除');
  }
  editAction(action: defs.Action) {
    if (!action.installed) {
      (this.$refs.actionForm as ActionForm).show(action);
      return;
    }
    this.$message.warning('内置操作不能编辑');
  }
  expand(expanded: boolean, module: defs.Module) {
    if (expanded && !(module as any).actions) {
      this.$api.module.actions(module.id || 0, ({data: actions}) => {
        //此处无法触发渲染更新
        Object.assign(module, {actions: actions});
        this.AppModule.refresh();
      });
    }
  }
  deleteModule(id: number) {
    this.$api.module.remove(id, () => {
      this.$message.success(
        this.$t('page.module.message.deleteSuccess'),
        2,
        () => {
          this.loadModules();
        }
      );
    });
  }

  loadModules() {
    this.$api.module.search(
      {page: this.pager.pageNumber, size: this.pager.pageSize, key: this.key},
      ({data}) => {
        this.pager = data;
      }
    );
  }
}
</script>
<style lang="less" scoped></style>
