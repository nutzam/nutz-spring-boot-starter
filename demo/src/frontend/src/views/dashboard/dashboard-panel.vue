<template>
  <page-container :title="$t(String($route.meta.title))" sub-title="系统控制台">
    <div style="min-height: calc(100vh - 218px)">
      <a-row :gutter="[12, 12]">
        <a-col :span="4">
          <a-statistic title="Active Users" :value="112893" />
        </a-col>
        <a-col :span="4">
          <a-statistic title="Feedback" :value="11.28" :precision="2" suffix="%" :value-style="{ color: '#3f8600' }">
            <template #prefix>
              <arrow-up-outlined />
            </template>
          </a-statistic>
        </a-col>
        <a-col :span="4">
          <a-statistic title="Account Balance (CNY)" :precision="2" :value="112893" />
        </a-col>
        <a-col :span="4">
          <a-statistic
            title="Idle"
            :value="9.3"
            :precision="2"
            suffix="%"
            class="demo-class"
            :value-style="{ color: '#cf1322' }"
          >
            <template #prefix>
              <arrow-down-outlined />
            </template>
          </a-statistic>
        </a-col>
        <a-col :span="4">
          <a-statistic-countdown title="Million Seconds" :value="deadline" format="HH:mm:ss:SSS" />
        </a-col>
        <a-col :span="4">
          <a-statistic-countdown :value="deadline">
            <template #title>
              <span>Countdown</span>
              <a-tooltip placement="right">
                <template #title>
                  <span>hurry up!</span>
                </template>
                <question-circle-two-tone style="margin-left: 5px" />
              </a-tooltip>
            </template>
          </a-statistic-countdown>
        </a-col>
        <a-col :span="12">
          <v-chart class="chart" :option="option" />
        </a-col>
        <a-col :span="12">
          <v-chart class="chart" :option="scatterData" autoresize />
        </a-col>
        <a-col :span="12"><v-chart class="chart" :option="option" /></a-col>
        <a-col :span="12">
          <v-chart class="chart" :option="option" />
        </a-col>
      </a-row>
    </div>
  </page-container>
</template>
<script lang="ts" setup>
import { ArrowUpOutlined, ArrowDownOutlined, QuestionCircleTwoTone } from '@ant-design/icons-vue';
import { scatterData } from './scatter';
const deadline = Date.now() + 1000 * 60 * 60 * 24 * 2 + 1000 * 30;
const option = ref({
  title: {
    text: 'Traffic Sources',
    left: 'center',
  },
  tooltip: {
    trigger: 'item',
    formatter: '{a} <br/>{b} : {c} ({d}%)',
  },
  legend: {
    orient: 'vertical',
    left: 'left',
    data: ['Direct', 'Email', 'Ad Networks', 'Video Ads', 'Search Engines'],
  },
  series: [
    {
      name: 'Traffic Sources',
      type: 'pie',
      radius: '55%',
      center: ['50%', '60%'],
      data: [
        { value: 335, name: 'Direct' },
        { value: 310, name: 'Email' },
        { value: 234, name: 'Ad Networks' },
        { value: 135, name: 'Video Ads' },
        { value: 1548, name: 'Search Engines' },
      ],
      emphasis: {
        itemStyle: {
          shadowBlur: 10,
          shadowOffsetX: 0,
          shadowColor: 'rgba(0, 0, 0, 0.5)',
        },
      },
    },
  ],
});
</script>
<style lang="less" scoped>
.chart {
  height: 35vh;
  background-color: #fff;
}
.ant-statistic {
  background-color: #fff;
  padding-left: 10px;
}
</style>
