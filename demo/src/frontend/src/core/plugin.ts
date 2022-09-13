import type { VXETableGlobalConfig, VXETableSetupOptions } from 'vxe-table';

export interface Plugin {
  vxe?: VXETableGlobalConfig | VXETableSetupOptions;
}

export const plugin: Plugin = {
  vxe: {
    grid: { stripe: true, border: true, highlightHoverColumn: true, highlightCurrentColumn: true },
    table: { stripe: true, border: true, highlightHoverColumn: true, highlightCurrentColumn: true },
  },
};
