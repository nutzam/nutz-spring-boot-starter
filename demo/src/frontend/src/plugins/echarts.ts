import type { App } from 'vue';
import ECharts from 'vue-echarts';
import { use } from 'echarts/core';
import { BarChart } from 'echarts/charts';
import { CanvasRenderer } from 'echarts/renderers';
import { PieChart } from 'echarts/charts';
import { TitleComponent, TooltipComponent, LegendComponent } from 'echarts/components';
use([CanvasRenderer, PieChart, BarChart, TitleComponent, TooltipComponent, LegendComponent]);

export default {
  install: (app: App) => {
    app.component('VChart', ECharts);
  },
};
