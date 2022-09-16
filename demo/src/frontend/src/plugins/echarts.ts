import type { App } from 'vue';
import ECharts from 'vue-echarts';
import { use } from 'echarts/core';
import { CanvasRenderer } from 'echarts/renderers';
import { PieChart, ScatterChart, BarChart } from 'echarts/charts';
import { TitleComponent, TooltipComponent, LegendComponent } from 'echarts/components';
use([CanvasRenderer, PieChart, BarChart, ScatterChart, TitleComponent, TooltipComponent, LegendComponent]);

export default {
  install: (app: App) => {
    app.component('VChart', ECharts);
  },
};
