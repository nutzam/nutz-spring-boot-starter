<template>
  <page-container :title="$t(String($route.meta.title))" sub-title="show current project dependencies">
    <a-card title="Project Dependencies">
      <a-row :gutter="[12, 12]">
        <a-col :span="12">
          <p v-for="(dep, key) in dependencies" :key="key">
            <strong style="margin-right: 12px">{{ key }}:</strong>
            <a-tag>{{ dep }}</a-tag>
          </p>
        </a-col>
        <a-col :span="12">
          <p v-for="(dep, key) in devDependencies" :key="key">
            <strong style="margin-right: 12px">{{ key }}:</strong>
            <a-tag>{{ dep }}</a-tag>
          </p>
        </a-col>
      </a-row>
    </a-card>
  </page-container>
</template>

<script lang="ts" setup>
import { dependencies, devDependencies } from '../../../package.json';
</script>
