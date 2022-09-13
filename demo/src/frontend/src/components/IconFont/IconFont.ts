import { createFromIconfontCN } from '@ant-design/icons-vue';
import { config } from '@/core/settings';
export const IconFont = createFromIconfontCN({
  scriptUrl: config.layout.iconfontUrl,
});
